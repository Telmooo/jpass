package jpass.util;

import static jpass.util.StringUtils.stripNonValidXMLCharacters;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class StringUtilsTest {

    String generateValidString() {
        int lowerBound = 32;     // Space
        int upperBound = 126;   // '~'
        int stringLength = 25;
        Random random = new Random();

        return random.ints(lowerBound, upperBound + 1)
                .limit(stringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


    @Test
    public void testNullStripNonValidXMLCharacters() {
        String nullOutput = stripNonValidXMLCharacters(null);

        Assertions.assertEquals("", nullOutput);
    }

    @Test
    public void testEmptyStringStripNonValidXMLCharacters() {
        String emptyStr = "";

        String emptyOutput = stripNonValidXMLCharacters(emptyStr);

        Assertions.assertEquals("", emptyOutput);
    }

    @RepeatedTest(10)
    public void testValidInputStripNonValidXMLCharacters() {
        String alphaNumeric = generateValidString();

        String output = stripNonValidXMLCharacters(alphaNumeric);

        Assertions.assertEquals(alphaNumeric, output);
    }

    @Test
    public void testInvalidInputStripNonValidXMLCharacters() {
        String invalidChar = "\uD800\uDC00"; // Unicode Character 'LINEAR B SYLLABLE B008 A' (U+10000)

        String output = stripNonValidXMLCharacters(invalidChar);

        Assertions.assertNotEquals(invalidChar, output);
    }

    @ParameterizedTest
    @ValueSource(strings = { "09", "0A", "0D", "20", "D7FF", "E000", "FFFD", "A000", "F000" })
    public void testValidBytesStripNonValidXMLCharacters(String toTest) {
        String input = String.valueOf((char)Integer.parseInt(toTest, 16));

        Assertions.assertEquals(input, StringUtils.stripNonValidXMLCharacters(input));
    }

    @ParameterizedTest
    @ValueSource(strings = { "08", "0B", "0C", "0E", "1F", "D800", "DFFF", "FFFE" })
    public void testNonValidBytesStripNonValidXMLCharacters(String toTest) {
        String input = String.valueOf((char)Integer.parseInt(toTest, 16));

        Assertions.assertNotEquals(input, StringUtils.stripNonValidXMLCharacters(input));
    }
    @ValueSource(ints = {-1, 0, 1})
    public void testStripStringNull(int length) {
        Assertions.assertNull(StringUtils.stripString(null, length));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void testStripStringEmpty(int length) {
        Assertions.assertEquals("", StringUtils.stripString("", length));
    }

    @Test
    public void testStripStringLength0() {
        Assertions.assertEquals("...", StringUtils.stripString("a", 0));
    }

    @Test
    public void testStripStringEqualLengths() {
        Assertions.assertEquals("a", StringUtils.stripString("a", 1));
    }

    @Test
    public void testStripStringGreaterLength() {
        Assertions.assertEquals("a...", StringUtils.stripString("ab", 1));
    }

    @Test
    public void testStripStringException() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> StringUtils.stripString("", -1));
    }
}
