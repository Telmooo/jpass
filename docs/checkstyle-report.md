# Checkstyle

Checkstyle is a static testing tool that focuses on consistency regarding coding standards. It has a high degree of configuration, allowing the activation or deactivation of individual patterns, or even their customization by changing their properties.
In this project, the configuration file (`rulesets/checkstyle-rules.xml`) was taken from the `jpacman` project of the [first recitation class](`https://paginas.fe.up.pt/~jcmc/tvvs/2022-2023/recitations/recitation-1.html`). In the end, 2 of the modules were deactivated and 1 was changed:
- [JavadocParagraph](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocParagraph) (line 324 of `checkstyle-rules.xml`) was deactivated because, in the project, after `<p>` , which contradicts the rule behaviour of checking if "Each paragraph but the first has `<p>` immediately before the first word, with no space after."
- [EmptyLineSeparator](https://checkstyle.sourceforge.io/config_whitespace.html#EmptyLineSeparator) (line 143-149 of `checkstyle-rules.xml`) was also deactivated. This rule implies the existence of an empty line in several places. It would flag dozens of violations in the project due to the fact that every file starts with a block of text regarding licensing of the app which is immediately followed (in most cases) by the `package` statement (without the existence of a line separating the two elements).
- [LineLength](https://checkstyle.sourceforge.io/config_sizes.html#LineLength) (line 47 of `checkstyle-rules.xml`) property value was changed from 100 to 150 to correct warnings related to character line length. Further explanation is given later in this report (and in the respective [issue](https://github.com/Telmooo/jpass/issues/11)).
- [customImportOrderRules](https://checkstyle.sourceforge.io/config_imports.html#CustomImportOrder_Properties) (line 277 of `checkstyle-rules.xml`) was changed from `<property name="customImportOrderRules" value="STATIC###THIRD_PARTY_PACKAGE"/>` to
`<property name="customImportOrderRules" value="STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE"/>`, i.e., "STANDARD_JAVA_PACKAGE" was added in order to, in our opinion, improve visual clarity, by separating the imports into 3 groups: `static` imports, `java` and `javax`, and the other imports.


## Report (0.1.21-SNAPSHOT)
Go to [full_report](./reports/checkstyle-0.1.21-SNAPSHOT.pdf) for a detailed overview of the 342 warnings that were triggered initially by Checkstyle (before the disabling/change of the previously mentioned rules). To note that imports' ordering and line lengths related violations were the 2 most reported.

A summary of the number of violations per file can be seen below:  

| **Files** | **Info** | **Warnings** |    **Errors**    |
|:---------:|:--------:|:------------:|:----------------:|
|    37     |    0     |     342      |        0         |


|                    **File**                    | **Warnings** |
|:----------------------------------------------:|:------------:|
|                **jpass.JPass**                 |      10      |
|             **jpass.crypt.Aes256**             |      18      |
|              **jpass.crypt.Cbc**               |      18      |
|        **jpass.crypt.DecryptException**        |      1       |
|      **jpass.crypt.io.CryptInputStream**       |      10      |
|      **jpass.crypt.io.CryptOutputStream**      |      5       |
|            **jpass.data.DataModel**            |      2       |
|        **jpass.data.EntriesRepository**        |      6       |
|       **jpass.ui.CopiablePasswordField**       |      1       |
|         **jpass.ui.EntryDetailsTable**         |      8       |
|            **jpass.ui.EntryDialog**            |      14      |
|      **jpass.ui.GeneratePasswordDialog**       |      8       |
|            **jpass.ui.JPassFrame**             |      32      |
|           **jpass.ui.MessageDialog**           |      19      |
|            **jpass.ui.SearchPanel**            |      5       |
|            **jpass.ui.StatusPanel**            |      4       |
|           **jpass.ui.SvgImageIcon**            |      18      |
|       **jpass.ui.TextComponentFactory**        |      2       |
|     **jpass.ui.action.AbstractMenuAction**     |      1       |
|       **jpass.ui.action.CloseListener**        |      2       |
|       **jpass.ui.action.MenuActionType**       |      31      |
|       **jpass.ui.action.TableListener**        |      4       |
|    **jpass.ui.action.TextComponentAction**     |      3       |
|  **jpass.ui.action.TextComponentActionType**   |      16      |
| **jpass.ui.action.TextComponentPopupListener** |      11      |
|           **jpass.ui.action.Worker**           |      2       |
|        **jpass.ui.helper.EntryHelper**         |      12      |
|         **jpass.ui.helper.FileHelper**         |      29      |
|         **jpass.util.ClipboardUtils**          |      3       |
|          **jpass.util.Configuration**          |      4       |
|           **jpass.util.CryptUtils**            |      3       |
|            **jpass.util.DateUtils**            |      6       |
|         **jpass.util.SpringUtilities**         |      15      |
|           **jpass.util.StringUtils**           |      3       |
|           **jpass.xml.bind.Entries**           |      6       |
|            **jpass.xml.bind.Entry**            |      8       |
|      **jpass.xml.converter.XmlConverter**      |      2       |


## [WhitespaceAround] '{' not preceded with whitespace
Go to [issue](https://github.com/Telmooo/jpass/issues/9).

The violation reported ([WhitespaceAround](https://checkstyle.sourceforge.io/config_whitespace.html#WhitespaceAround)) was present in file `src/main/java/jpass/xml/bind/Entry.java`, in lines 50, 153, 162, and 171. It was related to the non-existence of a whitespace before the token '{' in some function declarations.
As it is just a visual detail, it does not interfere with the application execution, but it is important to address to keep the code consistent across the project. Therefore, the white spaces missing were added.

```diff
-    public Entry(){
+    public Entry() {

(...)

-    public String getCreationDate(){
+    public String getCreationDate() {

(...)

-    public void setCreationDate(String date){
+    public void setCreationDate(String date) {

(...)

-    public String getLastModification(){
+    public String getLastModification() {
```

## [LineLength] Line is longer than 100 characters
Go to [issue](https://github.com/Telmooo/jpass/issues/11).

Checkstyle displayed warnings concerning the length of several lines of code being over 100 characters.
Due to the diversity of situations in which this warning was detected, it was considered a false-positive. An example was the line of a method definition where a possible solution would be to rename the method parameters to a more concise number of characters, but that would perhaps incur in confusing or unclear variable names.
To address this situation, the solution we chose was changing the property value of the rule from 100 to 150 in line 47 of the file `checkstyle-rules.xml` (as shown below):

```diff
-    <property name="max" value="100"/>
+    <property name="max" value="150"/>
```

Like the previous warning (as well as the following 3 entries), this violation does not affect the functioning of the program.

## [LocalVariableName/ParameterName] Naming convention
Go to [issue](https://github.com/Telmooo/jpass/issues/13).

Note: this entry joins 2 distinct in name but identical in practice warnings (same violation message and fix): [LocalVariableName](https://checkstyle.sourceforge.io/config_naming.html#LocalVariableName) and [ParameterName](https://checkstyle.sourceforge.io/config_naming.html#ParameterName).

In `jpass.util.SpringUtilities`, there were 4 local variables and 4 parameters whose name did not match the pattern '^[a-z]([a-z0-9][a-zA-Z0-9]*)?$'. An example for each was yPadSpring and xPad.

As this rule was respected across the rest of the project, the variables' names were changed to adhere to the Checkstyle presented convention. One of these changes can be seen below:
```diff
-     * @param xPad x padding between cells
-     * @param yPad y padding between cells
+     * @param xpad x padding between cells
+     * @param ypad y padding between cells
     */
-    public static void makeGrid(Container parent, int rows, int cols, int initialX, int initialY, int xPad, int yPad) {
+    public static void makeGrid(Container parent, int rows, int cols, int initialX, int initialY, int xpad, int ypad) {
```

## [JavadocTagContinuationIndentation] Line continuation has incorrect indentation level
Go to [issue](https://github.com/Telmooo/jpass/issues/14).

Checkstyle's [JavadocTagContinuationIndentation](https://checkstyle.sourceforge.io/config_javadoc.html#JavadocTagContinuationIndentation) rule pointed out that the "Javadoc line continuation indentation was expected to be at level 4 but there was no indent" in several instances:
- `jpass.crypt.Aes256` (2 occurrences; line 208, 224)
- `jpass.crypt.io.CryptOutputStream` (1 occurrence; line 116)
- `jpass.ui.EntryDialog` (1 occurrence; line 282)
- `jpass.ui.GeneratePasswordDialog` (1 occurrence; line 126)

As fixing this warning would improve the documentation text clarity, we corrected the situations, adding the due indents. Below are the examples regarding file `jpass.crypt.Aes256.java`:
```diff
     * @param value array in which the first {@code WORD_SIZE} {@code byte}s will be substituted.
-     * This array will be modified.
+     *     This array will be modified.

(...)

     * @param value Array in which the first {@code WORD_SIZE} {@code byte}'s will be changed due to
-     * the rotation. The contents of this array is changed by this invocation.
+     *     the rotation. The contents of this array is changed by this invocation.
```

## [CustomImportOrder] Imports Inconsistency
Go to [issue](https://github.com/Telmooo/jpass/issues/12).

The reported violations ([customImportOrderRules](https://checkstyle.sourceforge.io/config_imports.html#CustomImportOrder_Properties)) were related to inconsistency in the order of import statements: some files with alphabetically sorted imports, and others with the same prefix separated are some examples.

In order to improve organization, the [customImportOrderRules](https://checkstyle.sourceforge.io/config_imports.html#CustomImportOrder_Properties) was changed, as detailed in the beginning of the Checkstyle section of this report.

The final outcome was an import section in each file that followed a structure in 3 groups: static imports, standard imports (`java`/`javax`) and other imports.
The changes that, for example, file `jpass\src\main\java\jpass\ui\JPassFrame.java` went through can be seen below:

```diff
- import jpass.data.DataModel;
- import jpass.ui.action.CloseListener;
- import jpass.ui.action.MenuActionType;
- import jpass.ui.helper.EntryHelper;
- import jpass.ui.helper.FileHelper;
- import jpass.util.Configuration;
- import jpass.xml.bind.Entry;
+ import static jpass.ui.MessageDialog.NO_OPTION;
+ import static jpass.ui.MessageDialog.YES_NO_CANCEL_OPTION;
+ import static jpass.ui.MessageDialog.YES_OPTION;
+ import static jpass.ui.MessageDialog.getIcon;
+ import static jpass.ui.MessageDialog.showQuestionMessage;

 import java.awt.BorderLayout;
 import java.awt.Dimension;
 @ -45,22 +43,23 @@ import java.util.Comparator;
 import java.util.List;
 import java.util.logging.Level;
 import java.util.logging.Logger;
- 
 import javax.swing.JFrame;
- import javax.swing.JTable;
 import javax.swing.JMenu;
 import javax.swing.JMenuBar;
 import javax.swing.JPanel;
 import javax.swing.JPopupMenu;
 import javax.swing.JScrollPane;
+ import javax.swing.JTable;
 import javax.swing.JToolBar;
 import javax.swing.WindowConstants;

- import static jpass.ui.MessageDialog.NO_OPTION;
- import static jpass.ui.MessageDialog.YES_NO_CANCEL_OPTION;
- import static jpass.ui.MessageDialog.YES_OPTION;
- import static jpass.ui.MessageDialog.getIcon;
- import static jpass.ui.MessageDialog.showQuestionMessage;
+ import jpass.data.DataModel;
+ import jpass.ui.action.CloseListener;
+ import jpass.ui.action.MenuActionType;
+ import jpass.ui.helper.EntryHelper;
+ import jpass.ui.helper.FileHelper;
+ import jpass.util.Configuration;
+ import jpass.xml.bind.Entry;
```

## Report (0.1.22-SNAPSHOT)
After the fixes above, the number of violations reported decreased to **85**.

Go to [full report](./reports/checkstyle-0.1.22-SNAPSHOT.pdf).
