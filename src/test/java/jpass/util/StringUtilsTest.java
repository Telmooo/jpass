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


    // Boundary Value Analysis Tests
    @Test
    public void testCase1() { // 0x9
        // String input = "\u0009";
        String input = ""+(char)Integer.parseInt("9",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertEquals("\t", output);
    }
    @Test
    public void testCase2() { // 0xA
        String input = ""+(char)Integer.parseInt("A",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertEquals("\n", output);
    }

    @Test
    public void testCase3() { // 0xD
        String input = ""+(char)Integer.parseInt("D",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertEquals("\r", output);
    }

    @Test
    public void testCase4() { // 0x20
        String input = ""+(char)Integer.parseInt("20",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertEquals(" ", output);
    }

    @Test
    public void testCase5() { // 0xD7FF
        String input = ""+(char)Integer.parseInt("D7FF",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertEquals(input, output);
    }

    @Test
    public void testCase6() { // 0xE000
        String input = ""+(char)Integer.parseInt("E000",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertEquals(input, output);
    }

    @Test
    public void testCase7() { // 0xFFFD
        String input = ""+(char)Integer.parseInt("FFFD",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertEquals(input, output);
    }

    @Test
    public void testCase8() { // 0x8
        String input = ""+(char)Integer.parseInt("8",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertNotEquals(input, output);
    }

    @Test
    public void testCase9() { // 0xB
        String input = ""+(char)Integer.parseInt("B",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertNotEquals(input, output);
    }

    @Test
    public void testCase10() { // 0xC
        String input = ""+(char)Integer.parseInt("C",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertNotEquals(input, output);
    }

    @Test
    public void testCase11() { // 0xE
        String input = ""+(char)Integer.parseInt("E",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertNotEquals(input, output);
    }

    @Test
    public void testCase12() { // 0x1F
        String input = ""+(char)Integer.parseInt("1F",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertNotEquals(input, output);
    }

    @Test
    public void testCase13() { // 0xD800
        String input = ""+(char)Integer.parseInt("D800",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertNotEquals(input, output);
    }

    @Test
    public void testCase14() { // 0xDFFF
        String input = ""+(char)Integer.parseInt("DFFF",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertNotEquals(input, output);
    }

    @Test
    public void testCase15() { // 0xFFFE
        String input = ""+(char)Integer.parseInt("FFFE",16);

        String output = stripNonValidXMLCharacters(input);

        Assertions.assertNotEquals(input, output);
    }

    // testCase 16 and 17 (null and empty string) are already present
    // (testNullStripNonValidXMLCharacters
    // and
    // testEmptyStringStripNonValidXMLCharacters, respectively)
}
