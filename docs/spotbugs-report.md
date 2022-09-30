# SpotBugs
SpotBugs is a static testing tool for Java programs. SpotBugs "spots" bug patterns in the code that may potentially be errors.
There was no custom configuration done to SpotBugs, everything is running as default.

## Report (0.1.21-SNAPSHOT)
Go to [full report](./reports/spotbugs-0.1.21-SNAPSHOT.pdf).

The majority of bugs are related to potentially exposing internal representations of instances by either returning or storing references to mutable objects.
A quick rundown on the type of errors reported can be seen in the tables below:

| **Classes** | **Bugs** | **Errors** | **Missing Classes** |
| :---------: | :------: | :--------: | :-----------------: |
|     74      |    27    |     0      |          0          |

|                  **Class**                  | **Bugs** |
| :-----------------------------------------: | :------: |
|             **jpass.crypt.Cbc**             |    1     |
|          **jpass.data.DataModel**           |    3     |
|     **jpass.ui.GeneratePasswordDialog**     |    1     |
|           **jpass.ui.JPassFrame**           |    5     |
|          **jpass.ui.SvgImageIcon**          |    1     |
|     **jpass.ui.action.MenuActionType**      |    1     |
| **jpass.ui.action.TextComponentActionType** |    1     |
|         **jpass.ui.action.Worker**          |    1     |
|       **jpass.ui.helper.FileHelper**        |    2     |
|      **jpass.ui.helper.FileHelper$1**       |    1     |
|      **jpass.ui.helper.FileHelper$2**       |    1     |
|      **jpass.ui.helper.FileHelper$3**       |    1     |
|      **jpass.ui.helper.FileHelper$4**       |    1     |
|      **jpass.ui.helper.FileHelper$5**       |    1     |
|        **jpass.util.Configuration**         |    1     |
|          **jpass.util.DateUtils**           |    3     |
|         **jpass.util.StringUtils**          |    1     |
|         **jpass.xml.bind.Entries**          |    1     |

**(26 Medium Priority, 1 High Priority)**

|    **Category**    | **Bugs** | **Medium Priority** | **High Priority** |
| :----------------: | :------: | :-----------------: | :---------------: |
| **MALICIOUS_CODE** |    13    |         13          |         0         |
|  **CORRECTNESS**   |    1     |          1          |         0         |
|  **PERFORMANCE**   |    1     |          1          |         0         |
|  **BAD_PRACTICE**  |    7     |          1          |         0         |
|  **EXPERIMENTAL**  |    1     |          1          |         0         |
|     **STYLE**      |    4     |          3          |         1         |

## [EI_EXPOSE_REP] Methods returning references to internal mutable objects
Go to [issue](https://github.com/Telmooo/jpass/issues/6).

This bug denotes methods that return references to mutable objects, potentially exposing internal representations of those instances to unwanted modifications from external actors.

The following classes were affected by this bug:
- `jpass.ui.JPassFrame` (5 occurrences; line 230, 239, 316, 349, 221);
- `jpass.data.DataModel` (1 occurrences; line 123);
- `jpass.ui.action.MenuActionType` (1 occurrence; line 226);
- `jpass.ui.action.TextComponentActionType` (1 occurrence; line 212);
- `jpass.xml.bind.Entries` (1 occurrence; line 56).

However, we didn't consider all of them as true positive bug reports, for example, in the class `jpass.xml.bind.Entries` the method getting flagged for this bug returns a reference to an Entry list as intentional behaviour, as described by its Javadoc:
```
 * This accessor method returns a reference to the live list, not a snapshot. Therefore any
 * modification you make to the returned list will be present inside the object. This is
 * why there is not a {@code set} method for the entry property.
```
Similar cases happen for all the other classes except `jpass.data.DataModel`, where the data model was exposing the stored password via its getter by returning a reference to it. This may cause unwanted modifications to the password without explicitly using the setter method, and thus was a true positive report of this type of bug.

In order to address this issue on the relevant classes, it was changed to return a clone of the mutable object instead of the reference to the own object, as shown below in the diff between the versions:
```diff
    public byte[] getPassword() {
-       return this.password;
+       return this.password.clone();
    }
```

## [SBSC_USE_STRINGBUFFER_CONCATENATION] jpass.ui.GeneratePasswordDialog Non-performant operation on password generation
Go to [issue](https://github.com/Telmooo/jpass/issues/5).

This bug denotes a string being concatenated inside a loop. This causes a performance issue on Java due to the inner workings of string concatenation, where in each iteration the string object is converted to a `StringBuilder`, appended, and then converted back to a `String` object. This transforms a linear cost to the number of iterations to a quadratic cost, due to the string being copied on each iteration.

In the context of the project, the bug is present at `jpass.ui.GeneratePasswordDialog` when generating the valid character set to be used for generating a random password, and to address that, it was changed to a `StringBuilder` object to avoid the hidden conversions and copies of the string on each iteration, as show in the diff below:
```diff
    } else if ("generate_button".equals(command)) {
-       String characterSet = "";
+       StringBuilder characterSet = new StringBuilder();
        for (int i = 0; i < PASSWORD_OPTIONS.length; i++) {
            if (this.checkBoxes[i].isSelected()) {
-               characterSet += PASSWORD_OPTIONS[i][1];
+               characterSet.append(PASSWORD_OPTIONS[i][1]);
            }
        }

        if (this.customSymbolsCheck.isSelected()) {
-           characterSet += this.customSymbolsField.getText();
+           characterSet.append(this.customSymbolsField.getText());
        }

-       if (characterSet.isEmpty()) {
+       if (characterSet.length() == 0) {
            MessageDialog.showWarningMessage(this, "Cannot generate password.\nPlease select a character set.");
            return;
        }
```

## [VA_FORMAT_STRING_USES_NEWLINE] Using system-specific newline instead of portable newline
Go to [issue](https://github.com/Telmooo/jpass/issues/4).

This bug denotes a format string using the newline character `\n`, which always generates the byte code `U+000A`. However, this byte code is system-dependent and may not generate a newline in different operating systems to Unix.

Therefore, it is recommended to use the special character `%n` that produces the platform-specific line separator.

This bug was present in the class `jpass.ui.helper.FileHelper` properties and was addressed by changing to the portable newline character, as recommended by Java specification:
```diff
    public static final String SAVE_MODIFIED_QUESTION_MESSAGE =
-           "The current file has been modified.\n" +
+           "The current file has been modified.%n" +
            "Do you want to save the changes before closing?";
    private static final String UNENCRYPTED_DATA_WARNING_MESSAGE =
-           "Please note that all data will be stored unencrypted.\n" +
+           "Please note that all data will be stored unencrypted.%n" +
            "Make sure you keep the exported file in a secure location.";
    private static final String OPEN_ERROR_CHECK_PASSWORD_WARNING_MESSAGE =
-           "An error occured during the open operation.\nPlease check your password.";
+           "An error occured during the open operation.%nPlease check your password.";
    private static final String CREATE_FILE_QUESTION_MESSAGE =
-           "File not found:\n%s\n\nDo you want to create the file?";
+           "File not found:%n%s%n%nDo you want to create the file?";
    private static final String OPERATION_ERROR_MESSAGE =
-           "An error occured during the %s operation:\n%s";
+           "An error occured during the %s operation:%n%s";
    private static final String FILE_OVERWRITE_QUESTION_MESSAGE =
-           "File is already exists:\n%s\n\nDo you want to overwrite?";
+           "File is already exists:%n%s%n%nDo you want to overwrite?";

    private static final String JPASS_DATA_FILES = "JPass Data Files (*.jpass)";
    private static final String XML_FILES = "XML Files (*.xml)";
```

## [UC_USELESS_CONDITION_TYPE] util.StringUtils not handling UTF-16 correctly
Go to [issue](https://github.com/Telmooo/jpass/issues/3).

Historically, Java represented Unicode characters using a 16-bit data type _char_. All standard UTF-16 characters are between the range of `0` (`0x0000`) to `65535` (`0xFFFF`), which consists of the Basic Multilingual Plane (BMP).

However, with the expansion of Unicode, each supplementary character, this is, whose code points are above `0xFFFF` are represented as [surrogate pairs](https://datacadamia.com/data/type/text/surrogate), as Java decided to not add a Char type of 32-bit. Surrogate pairs are special characters that are encoded with 4 bytes (one pair of 2 bytes) and are preceded by a high-surrogate (`\uD800-\uDBFF`) or a low-surrogate (`\uDC00-\uDFFF`). These characters are not printable, and, also considered invalid characters by [W3C recommendation](https://www.w3.org/TR/xml/#NT-Char) on XML parsing, however, the surrogate pair as a whole is considered valid.

The issue present in `stripNonValidXMLCharacters` method is the incorrect checking of valid characters by W3C recommendation due to not properly considering surrogate pair Unicode characters, as it uses a Char by Char iteration across the string to make the filtering.

Additionally, by reviewing the Javadoc for the method, it was identified an additional bug regarding the behaviour of the method on `null` or empty string input.

The documentation refers the behaviour in that case as such:
```
This method will return an empty String if the input is null or empty.
```
However, the method in the previous version was returning the input object itself, meaning it was returning a potential `null` object.

To address the first problem, there are two options:
- Change the behaviour to only support the characters defined in the BMP;
- Correct method, to properly handle surrogate pairs.

To address the second problem:
- Correct the first check (for null or empty string) to return a new empty string, rather than own input string object.

For the first problem, it was decided to go with the first option for the sole reason of the characters defined outside the BMP range of UTF-16 being characters that are barely supported by any font, and thus not appropriate to be accepted as valid characters despite W3C recommendation.

The diff can be seen below for how the bugs were addressed:
```diff
    /**
     * This method ensures that the output String has only valid XML unicode characters as specified
-    * by the XML 1.0 standard. For reference, please see
-    * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the standard</a>. This method
-    * will return an empty String if the input is null or empty.
+    * by the XML 1.0 standard, except the surrogate pair characters (characters outside the UTF-16 BMP).
+    * For reference, please see <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the standard</a>.
+    * This method will return an empty String if the input is null or empty.
     *
     * @param in The String whose non-valid characters we want to remove.
     * @return The in String, stripped of non-valid characters.
     */
    public static String stripNonValidXMLCharacters(final String in) {
        if (in == null || in.isEmpty()) {
-           return in;
+           return "";
        }
        StringBuilder out = new StringBuilder();
        char current;
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i);
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                    || ((current >= 0x20) && (current <= 0xD7FF))
-                   || ((current >= 0xE000) && (current <= 0xFFFD))
-                   || ((current >= 0x10000) && (current <= 0x10FFFF))) {
+                   || ((current >= 0xE000) && (current <= 0xFFFD))) {
                out.append(current);
            } else {
                out.append('?');
```

## [EI_EXPOSE_REP2] Instances may expose internal representation
Go to [issue](https://github.com/Telmooo/jpass/issues/2).

Similar to [EI_EXPOSE_REP](#ei_expose_rep-methods-returning-references-to-internal-mutable-objects), this bug denotes a class that is storing external mutable objects on its instance, potentially compromising its internal representation.

Classes affected:
- `jpass.crypt.Cbc` (1 occurrence; 109);
- `jpass.ui.action.Worker` (1 occurrence; line 56);
- `jpass.data.DataModel` (1 occurrence; line 127);

However, similar to earlier, the bugs identified in class `jpass.crypt.Cbc` and `jpass.ui.action.Worker` are false positives, since on both instances, it's intentional to store a mutable object. On the first, it is stored an output stream to where Cbc will write content, and on the second, it is stored the parent frame of the `Worker` on which the worker will act upon.

The bug identified on `jpass.data.DataModel` is a true positive, storing the external mutable object on the setter and potentially being vulnerable to password being changed after setter being invoked. To address this issue, it was changed to store a clone instead, as show in the diff below:
```diff
    public void setPassword(byte[] password) {
-       this.password = password;
+       this.password = password.clone();
    }
```

## Report (0.1.22-SNAPSHOT)
After the fixes above, the number of bugs decreased to **19**.

Go to [full report](./reports/spotbugs-0.1.22-SNAPSHOT.pdf).