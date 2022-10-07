import static jpass.util.StringUtils.stripNonValidXMLCharacters;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


public class validXMLCharsTest {

    String generateAlphanumeric() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }


    @Test
    public void testNull() {
        String nullOutput = stripNonValidXMLCharacters(null);

        Assertions.assertEquals("", nullOutput);
    }

    @Test
    public void testEmptyStr() {
        String emptyStr = "";

        String emptyOutput = stripNonValidXMLCharacters(emptyStr);

        Assertions.assertEquals("", emptyOutput);
    }

    @RepeatedTest(10)
    public void testValidInput() {
        String alphaNumeric = generateAlphanumeric();

        String output = stripNonValidXMLCharacters(alphaNumeric);

        Assertions.assertEquals(alphaNumeric, output);
    }

    @Test
    public void testInvalidInput() {
        String invalidChar = "\uD800\uDC00"; // Unicode Character 'LINEAR B SYLLABLE B008 A' (U+10000)

        String output = stripNonValidXMLCharacters(invalidChar);

        Assertions.assertNotEquals(invalidChar, output);
    }

}
