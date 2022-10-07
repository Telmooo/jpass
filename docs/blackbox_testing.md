# Black-box Testing

## Category Parition Testing

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
3. the equality comparison between the input and output strings return `true`;
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

