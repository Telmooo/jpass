# Black-box Testing

# Category Parition Testing

The project contains the following packages as candidates for black-box testing:
- `crypt` - This package was already tested in the original repository, and therefore was excluded from the possiblities;
- `data` - Only contains the data model for the application, and thus, only contains trivial methods relevant to data models (getters and setters). It isn't crucial to test these type of methods due to their triviality, and so, we excluded this package from the possibilities;
- `ui` - Contains the UI and all its interactions. A complex package, which might not be the most appropriate for black-box testing and category-partition testing due to its complexity and possible dependencies between classes and methods.
- `xml` - Similar to package `data`, mostly contains a data model for XML representation, and was deemed not crucial for testing;
- `util` - A diverse package with a collection of methods used in the project. This was the package chosen due to its simplicity of understanding of the method behaviour, and being the core methods for the processing of data in the program.

From the package `util`, it was decided to test the following methods:
- `StringUtils.stripNonValidXMLCharacters` - it was a method already explored before, and suffered changes in behaviour, thus making a prime candidate for testing;
- `CryptUtils.getPKCS5Sha256Hash` - method responsible for hashing the content, it's a core functionality of the program, and must be ensured it's working properly for security of users' data. Thus making it a prime candidate for testing;
- `DateUtils.createFormatter` and `DateUtils.formatIsoDateTime`- both methods are responsible for handling dates, and timestamps. While seemingly trivial methods, in theory, in practice these methods suffer from a lot of caveats and exceptional behaviours, and thus were chosen for category-partition testing due to better fitting this type of testing. 
- `ClipboardUtils.getClipboardContent` - chosen mostly by process of elimination, due to being one of the few non-trivial methods left in this package that were appropriate for this type of testing.

## Method `jpass.util.StringUtils.stripNonValidXMLCharacters`

### Method Purpose
`stripNonValidXMLCharacters` ensures that its output is composed only of valid XML characters described by [W3C documentation](https://www.w3.org/TR/xml/#NT-Char) except for characters outside the UTF-16 BMP, since the changed made in the relevant [issue](https://github.com/Telmooo/jpass/issues/3) to the documentation and behaviour of the method.

### Category-Partition
There is a single parameter - `final String in` - and an output (method return value) of the same class: `String`, and there aren't any restrictions in the contents or length of the parameter (except the theoretical maximum of 2 147 483 647 characters), nor exceptional behaviour.

As the function's goal is to output a string composed only of valid XML characters, and by looking at the documentation provided, the following division was reached:
1. the null case - where, according to the documentation, an empty string should be output;
2. the empty string - with expected behaviour identical to the null case;
3. valid input - input string with only valid characters, where it is expected that the input and output are identical. For this case, it was utilized an auxiliary method that generated a random string (with valid characters);
4. invalid input - input string with only invalid characters, where differences are expected between the input and output.

Test cases for the partitions described above were developed in this PR as follows:
1. Test is done by passing a null string as input of the method;
2. Test is done by passing an empty string as input of the method;
3. Test is set up with a repeated test, cycling random generated strings containing only valid characters by our specification, and testing for equality of strings, as no character should be switched;
4. Test is set up with the surrogate character mentioned in the [issue](https://github.com/Telmooo/jpass/issues/3) that addressed the behaviour of this method, and is tested for inequality of the strings (original and stripped).

All tests yielded the expected results (all passed):
1. an empty string `""` was returned;
2. an empty string `""` was returned;
3. the equality comparison between the input and output strings returns `true`;
5. the inequality comparison between the input and output returned `true`. The invalid characters of the input were in fact changed to question marks and, therefore, printing the output would show `?` characters.

Note regarding the 3rd case: even though it was most likely not necessary (due to the fact that it is usually expected that if an "a" is valid, a "b" will also be), test repetition was implemented for learning purposes (with a value of 10) as it didn't represent a relevant impact on performance (a few milliseconds, according to IntelliJ).

## Method `jpass.util.CryptUtils.getPKCS5Sha256Hash`

### Method Purpose
`getPKCS5Sha256Hash` calculates the SHA-256 hash with 1000 iterations of the input char array.

### Category-Partition
This method takes a single argument, the input text in `char[]` format, has exceptional behaviour, in which, it will throw an exception if any exception occurs during the hashing process, and returns the hashed result in `byte[]` format.

With this in mind, we can obtain the following partitions for testing:
1. the empty array case - in which the input array is an empty char array;
2. non-empty char array - in which the input array is any non-empty text in char array format;
3. null char array - in which the input array is a null char array.

Test cases for the partitions described above were developed in this PR as follows:
1. Test is done with an empty char array `new char[0]`;
2. Test is set up with a text in char array format, such as, [`the cake is a lie`](https://www.youtube.com/watch?v=qdrs3gr_GAs);
3. Test is set up with a clipboard containing a random image;

Method behaved as expected for all the tests developed:
1. Properly returned the correspondent 1000th iteration of SHA-256 hash of an empty text, which is not an empty hash, as expected;
2. Properly returned the correspondent 1000th iteration of SHA-256 hash of the input string [`the cake is a lie`](https://www.youtube.com/watch?v=qdrs3gr_GAs), as expected;
3. Throws an exception due to `NullPointerException`. Which is the expected result, given the method declaration denoting a possible exceptional behaviour.

Notes:
For test case 3, it was assumed that throwing an exception was the intended behaviour, as this method doesn't provide documentation for expected behaviour when facing a null input.

## Method `jpass.util.ClipboardUtils.getClipboardContent`

### Method Purpose
`getClipboardContent` extracts the current text stored in the system clipboard, if available.

### Category-Partition
This method takes no arguments, has no exceptional behaviour, and returns a String object containing the text content of the system clipboard, if available and exists, returning `null` otherwise.
With this in mind, we can obtain the following partitions for testing:
1. the empty clipboard case - where the system clipboard is completely empty and has no content, and therefore, should make the method return `null`;
2. clipboard with text content - where the system clipboard contains valid text content, and therefore, should make the method return that text;
3. clipboard with non-text content - where the system clipboard contains non-valid text content, such as, an image, a file, or similar, and therefore, should make the method return `null`.

Test cases for the partitions described above were developed in this PR as follows:
1. Test is done with a cleared clipboard;
2. Test is set up with a clipboard containing a string `a text string`;
3. Test is set up with a clipboard containing a random image;

Method behaved as expected for all the tests developed:
1. Properly returned `null` as expected;
2. Properly returned the content of the clipboard, `a text string`, as expected;
3. Properly returned `null`, as the content of the clipboard is not a valid text content.

Added unit testing for methods in `java.jpass.util.DateUtils`: `createFormatter`, `formatIsoDateTime`.

## Method `jpass.util.DateUtils.createFormatter`

### Method Purpose
`createFormatter` creates a `DateTimeFormatter` from a string representing a specific format.

### Category-Partition
The method has a single parameter(`String format`) and returns an object of the class `DateTimeFormatter`. If the value is not valid (e.g., is `null`), the function returns by default the formatter `DateTimeFormatter.ISO_DATE`. It doesn't have any exceptional behaviour.

Taking this into account, a division in partitions was reached:
1. null case - where a `NullPointerException` is raised, being expected to default to the `DateTimeFormatter.ISO_DATE` value.
2. invalid argument - raises a `IllegalArgumentException`, and, as the previous cases, the method is supposed to return the `DateTimeFormatter.ISO_DATE` value.
3. empty case - an empty string is provided. Its utility is virtually non-existent due to the fact that when applied to a date/time representing object, it yields an empty string. Nonetheless, it should be accepted as a normal argument, not affecting the normal behaviour of the function, as it wasn't found any information saying otherwise.
4. general valid case - where the input string is one of many possibilities of combinations of characters ([see DateTimeFormatter Class documentation for details](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html)) and the returned value should be the respective one, being possible to apply it to date and time representing `String` objects.

Test cases for the partitions mentioned were developed and are part of this pull request:
1. Test with a `null` string as the method argument. It's tested the equality of the formatter returned to the `DateTimeFormatter.ISO_DATE`, as that is the expected behaviour of the function.
2. Test where the argument takes the value `aaaaaa`, which is considered an invalid formatter (see section ["Patterns for Formatting and Parsing"](https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html)) for the `IllegalArgumentException` criteria. It's tested the equality of the formatter returned to the `DateTimeFormatter.ISO_DATE`, as that is the expected behaviour of the function.
3. Test where the argument is `""` - the empty string, and the output is tested by comparing equality of output of the default Formatter built by `DateTimeFormatterBuilder` to the one created.
4. Two test cases where a valid string is given as input: `yyyyMMdd` and `HH:mm:ss`. The testing is done by applying this formatter to the current date time and testing for equality of the output.

Note: For test case 4, it wasn't done an extensive testing for all the possible formats, as it was deemed not necessary and redundant with no real benefit from the additional testing cases.

The function had the expected behaviour for all tests:

1. `DateTimeFormatter.ISO_DATE` was returned as expected.
2. `DateTimeFormatter.ISO_DATE` was returned as expected.
3. the returned value is the same as a freshly created `DateTimeFormatter` object (obtainable by calling `new DateTimeFormatterBuilder().toFormatter()`)
4. in both test cases, the returned values were the ones obtainable by calling the respective predefined formatter: `BASIC_ISO_DATE` and `ISO_LOCAL_TIME`, respectively.


## Method `jpass.util.DateUtils.formatIsoDateTime`

### Method Purpose
Taking both a ISO local date time `String` object and a `DateTimeFormatter` object, this method converts the given string to the format provided in the formatter. For example, given the arguments:
- ISO datetime: `2018-07-14T17:45:55.9483536`,
- Formatter: `ISO_LOCAL_DATE`,
  should yield the resulting date string `2018-07-14`.

### Category-Partition
The method takes two arguments as input, as described above, and outputs the resulting string. If the datetime string given as argument isn't a ISO local datetime, the method defaults to Unix epoch and formats Unix epoch with the formatter given. It doesn't have any exceptional behaviour. From this, the following partitions were reached:
1. the null date string case - where the second parameter (formatter) is a valid one, but the first is null string, being expected that the function returns without raising any exception;
2. the null formatter case - where a valid date string and null value formatter parameter are provided;
3. invalid date time object case - where the datetime string provided isn't in the ISO local date time format;
4. valid case - where both arguments are provided and valid, being expected to return the string correctly formatted;

Test cases for the partitions described above were developed in this PR:
1. Test is set up so that given a `null` dateString value, it is checked if an exception was not thrown by the method.
2. Test is done with `null` as the formatter parameter value (and a valid dateString `2018-07-14T17:45:55.9483536`), and tested for equality to the output of the default formatter `ISO_LOCAL_DATE_TIME` (`2018-07-14T17:45:55`).
3. Test is done with `DateTimeFormatter.ISO_LOCAL_DATE` as the formatter parameter value and the invalid ISO local datetime `2018-07-14` value for dateString, and tested for equality with the Unix epoch `1970-01-01`, which is the expected result on error, with the given formatter.
4. Test with input string `2018-07-14T17:45:55.9483536` and formatter `DateTimeFormatter.ISO_LOCAL_DATE`, and tested for equality with the expected ISO local date string `2018-07-14`.

The method behaved as expected for all the tests developed except one:
1. No exception was thrown as expected;
2. A `NullPointerException` is thrown, and as a result, the test fails. This is due to the fact that there is no fail-safe for the possibility that the provided formatter is of `null` value and is a point to be corrected in the source code;
3. As expected, given the string (`2018-07-14`), the default value for that situation (`1970-01-01`) is returned;
4. The returned value was `2018-07-14`, as expected.

Note: We took the opportunity to correct the behaviour of the method for when given a null formatter as input argument to the following:
> When `formatter` is `null`, it is used the `ISO_LOCAL_DATE_TIME` formatter to format the datetime.

The code changed can be seen in the diff below:
```diff
                dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.systemDefault());
            }
        }
+       if (formatter == null) {
+           return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateTime.truncatedTo(ChronoUnit.SECONDS));
+       }

        return formatter.format(dateTime.truncatedTo(ChronoUnit.SECONDS));
    }
}
```

With this change, the same test developed above succeeds, as expected.

## Method `jpass.util.StringUtils.stripString`
See [issue](https://github.com/Telmooo/jpass/issues/28).

### Quick-fix
The method got a quick-fix on its behaviour, as the exceptional behaviour of the method wasn't properly marked as such. Instead of changing conditions it was decided to just mark the method with exceptional behaviour, as such:
```diff
- public static String stripString(String text, int length) {
+ public static String stripString(String text, int length) throws IndexOutOfBoundsException {
```

### Method Purpose
`stripString` is a method that makes use of the Java `substring` string procedure to reduce a string to the desired size, appending an ellipsis to it, if necessary. There are two inputs: an object of type `String` - the string to be trimmed -, and an `int` that corresponds to the maximum number of characters of the original string to keep.

### Category-Partition
There are two parameters - `String text` - which denotes the input string to be trimmed, and -- `int length` -- that denotes the maximum length the input string can have without being trimmed. Additionally, there is an exceptional behaviour, `IndexOutOfBoundsException`, if the length parameter is negative.
The output of the function is, therefore, the trimmed version of the input string. This leads to the following divisions in the following format [text case, length case]:
**NoC** = number of characters
1. null case, negative case - where the output should be null itself;
2. null case, zero case - where the output should be null itself;
3. null case, positive case - where the output should be null itself;
4. non-null NoC above the length parameter case, negative case - where an exceptional behaviour occurs;
5. non-null NoC below or equal the length parameter, zero case - where the output is the input string itself;
6. non-null NoC above the length parameter, zero case - where the output is `...`;
7. non-null NoC below or equal the length parameter, positive case - where the output is the input string itself;
8. non-null NoC above the length parameter, positive case - where the output is a string with N (N being the number passed in length parameter) characters from the input string followed by ellipsis.

# Boundary-Value Analysis Testing

## Method `jpass.util.StringUtils.stripString`
For category partition refer [here](#method-jpassutilstringutilsstripstring).

#### Boundary-Value Analysis

The input `int` length, _L_, can take negative (< 0), zero (0), or positive (> 0). By consequence, the input `String`, _S_, can take the values `null`, `""`, any string with length greater than L, and any string with length less or equal to L, as illustrated below:
![partitions](https://user-images.githubusercontent.com/44447503/197277583-17f9a5ad-80e9-4c7f-92b4-0be1b89603a2.png)


Noting that case E4 is practically impossible, since it's impossible to have a string with length lower or equal to L, given that L is a negative value, and so boundaries involving E4 don't exist (E1-E4, E4-E5, E4-E7). Therefore, we can derive the following boundaries from the partitions above:
#### Boundary E1-E2
Fixed values: S=null (value stays fixed on this boundary)
Condition: L<0
On-points:
- L - `0` (makes condition false).

Off-points:
- L - `-1` (makes condition true).

Generates 2 test cases:
T1. S=null, L=-1 (E1)
T2. S=null, L=0 (E2)

#### Boundary E2-E3
Fixed values: S=null (value stays fixed on this boundary)
Condition: L>0
On-points:
- L - `0` (makes condition false).

Off-points:
- L - `1` (makes condition true).

Generates 2 test cases:
T3. S=null, L=0 (E2) (same test as T2)
T4. S=null, L=1 (E3)

#### Boundary E2-E5
Fixed values: L=0 (value stays fixed on this boundary)
Condition: S=null
On-points:
- S - null (makes condition true).

Off-points:
- S - `""` (makes condition true).

Generates 2 test cases:
T5. S=null, L=0 (E2) (same test as T2 and T3)
T6. S="", L=0 (E5)

#### Boundary E3-E6
Fixed values: L>0 (value stays fixed on this boundary) (L=1)
Condition: S=null
On-points:
- S - null (makes condition true).

Off-points:
- S - `""` (makes condition true).

Generates 2 test cases:
T7. S=null, L=1 (E3) (same test as T4)
T8. S="", L=1 (E6)

#### Boundary E5-E6
Fixed values: S with length <= L (value stays fixed on this boundary) (S=`""`)
Condition: L>0
On-points:
- L - `0` (makes condition false).

Off-points:
- L - `1` (makes condition true).

Generates 2 test cases:
T9. S="", L=0 (E5) (same test as T6)
T10. S="", L=1 (E6) (same test as T8)

#### Boundary E5-E8
Fixed values: L=0 (value stays fixed on this boundary)
Condition: S with length <= L
On-point:
- S - `""` (makes condition true).
Off-points:
- S - `"a"` (makes condition false).

Generates 2 test cases:
T11. S="", L=0 (E5) (same test as T6 and T9)
T12. S="a", L=0 (E8)

#### Boundary E6-E9
Fixed values: L>0 (value stays fixed on this boundary) (L=1)
Condition: S with length <= L
On-point:
- S - `"a"` (makes condition true).

Off-points:
- S - `"ab"` (makes condition false).

Generates 2 test cases:
T13. S="a", L=1 (E6)
T14. S="ab", L=1 (E9)

#### Boundary E8-E9
Fixed values: S with length > L (value stays fixed on this boundary) (S="ab")
Condition: L>0
On-point:
- L - `0` (makes condition false).
Off-point:
- L - `1` (makes condition true).

Generates two test cases:
T15. S="ab", L=0 (E8) (same test, in behaviour, to test T12)
T16. S="ab", L=1 (E9) (same test as T14)

#### Boundary E7-E8
Fixed values: S with length > L (value stays fixed on this boundary) (S="")
Condition: L<0
On-point:
- L - `0` (makes condition false).

Off-point:
- L - `-1` (makes condition true).

Generate two test cases:
T17. S="", L=-1 (E7)
T18. S="", L=0 (E8) (same as test T6, T9 and T11)

### Implemented Tests
From the tests derived above, only the following tests need to be implemented, as others are redundant.

#### Test T1, T2, T4
Test is implemented with a parametrized test for testing the various lengths values on the null string, as the expected output for all these tests is the null string itself.
```java
@ParameterizedTest
@ValueSource(ints = {-1, 0, 1})
public void testStripStringNull(int length) {
    Assertions.assertNull(StringUtils.stripString(null, length));
}
```
All tests had the expected outcome without any failures.

#### Test T6, T8
Test is implemented with a parametrized test for testing the various lengths values on the string whose length is less or equal to the length specified. This can be done by using the empty string and testing the two lengths `0` and `1`. The expected output for all these tests is an empty string.
```java
@ParameterizedTest
@ValueSource(ints = {0, 1})
public void testStripStringEmpty(int length) {
    Assertions.assertEquals("", StringUtils.stripString("", length));
}
```
All tests had the expected outcome without any failures.

#### Test T12
Test is implemented to assert the output for any non-null and non-empty string with the length parameter equal to 0 is the string `...`.
```java
@Test
public void testStripStringLength0() {
    Assertions.assertEquals("...", StringUtils.stripString("a", 0));
}
```
The test had the expected outcome without any failure.

#### Test T13
Test is implemented to assert the output is the same as the input string, since the length of the input string is equal to the length parameter specified.
```java
@Test
public void testStripStringEqualLengths() {
    Assertions.assertEquals("a", StringUtils.stripString("a", 1));
}
```
The test had the expected outcome without any failure.

#### Test T14
The test is implemented to assert the input string is correctly trimmed, since the length of the input string is greater than the length parameter specified.
```java
@Test
public void testStripStringGreaterLength() {
    Assertions.assertEquals("a...", StringUtils.stripString("ab", 1));
}
```
The test had the expected outcome without any failure.

#### Test T17
The test is implemented to assert the exceptional behaviour when passing a negative value on the length parameter with a non-null string as input.
```java
@Test
public void testStripStringException() {
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> StringUtils.stripString("", -1));
}
```
The test had the expected outcome without any failure.
