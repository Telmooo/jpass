# Structural Testing
 
## Initial Coverage

By making use of the JaCoCo library, the following test coverage report was created, which concerns the tests that were already present in the project, in addition to the work developed in the previous assignments.

![beforeCoverage](./assets/beforeCoverage.png)

The package `jpass.crypt`, the one which out-of-the-box already had some tests created for it, is by far the one with the highest (line and branch) coverage.

## Features explored

The most used notation was `@Test` which describes a normal JUnit test.
When deemed useful, the `@BeforeEach` and `@AfterEach` notation were utilized, serving the respective methods as setup and cleanup functions, initializing a base state over which every test of that group will operate over, and cleaning up any residue left by the testing, respectively. `@BeforeAll` is also utilized to avoid trying to initialize more than once an object that is treated as a singleton in its class. There was also a situation where `@Nested` (and `@DisplayName`) was considered helpful as it allowed to compartmentalize some tests which required extra setup steps from the rest of the test suite of that class.
When it came to the tests themselves, `@ParameterizedTest` and consequently `@ValueSource`, as well as `@RepeatedTest` were JUnit annotations that are present in the developed test suite, avoiding unnecessary code repetition for almost identical tests where the input was the only difference.
Furthermore, and although not JUnit-related, worthy of mention are: the use of the Java mocking library Mockito to easily test some exceptional branches and the development of classes for testing such as `PasswordManagerUtil` to use in the `XmlConverter` test suite.

For test assertion, all types of assertions were explored through the developed test suite, such as `assertThrows` and `assertDoesNotThrow` to test for exceptional behaviours, `assertArrayEquals`, `assertEquals`, `assertNull`, `assertTrue`, among others for tests that involved checking for equality of outputs, etc.

## Final Coverage

As instructed in the assignment specification, the GUI-related classes were ignored for this coverage testing (package `jpass.ui`, and classes `jpass.util.SpringUtilities.class` and `jpass.JPass.class`), resulting in the following line and branch coverage:

![afterCoverage](./assets/afterCoverage.png)

Some lines and branches were not covered in testing, as they were deemed not feasible to test, for various reasons, such as:
- `jpass.util.CryptUtils.newRandomNumberGenerator` - only executes the exceptional branch if the default `SecureRandom` constructor fails. And the latter only fails in erroneous installations of Java, and therefore was not tested.
- `jpass.util.ClipboardUtils` - has various exceptional branches that only result in error if the clipboard is being used by another program at runtime. This behaviour was considered not feasible to test in JUnit testing.

Note: to obtain both reports, `mvn site` was executed, being the screenshots provided from the generated html.

In the submission, it is also included the site before the tests developed in this assignment, and the site after the tests developed in this assignment.