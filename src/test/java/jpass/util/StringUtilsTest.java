package jpass.util;

import static jpass.util.StringUtils.stripNonValidXMLCharacters;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


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

}