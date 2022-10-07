import static jpass.util.StringUtils.stripNonValidXMLCharacters;

import java.lang.annotation.Repeatable;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

/**
 * Input: string
 */
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
    public void testingNullAndEmpty() {
        String nullStr = null;
        String emptyStr = "";

        String nullOutput = stripNonValidXMLCharacters(nullStr);
        String emptyOutput = stripNonValidXMLCharacters(emptyStr);

        Assert.assertEquals("", nullOutput);
        Assert.assertEquals("", emptyOutput);
    }

    @Test
    public void testingAlphanumeric() {
        String alphaNumeric, output;
        int nTries = 50;

        for (int i = 0; i < nTries; i++) {
            alphaNumeric = generateAlphanumeric();
            output = stripNonValidXMLCharacters(alphaNumeric);
            // System.out.println(alphaNumeric);
            Assert.assertEquals(alphaNumeric, output);
        }
    }

    @Test
    public void testingInvalid() {
        String invalidChar = "\uD800\uDC00"; // Unicode Character 'LINEAR B SYLLABLE B008 A' (U+10000)
        String output = stripNonValidXMLCharacters(invalidChar);

        Assert.assertNotEquals(invalidChar, output);
    }

    @Test
    public void testingValidAndInvalid() {
        for (int i = 0; i < 10; i++) {
            String str = generateAlphanumeric();
            str += "-\uD806\uDE00";

            String output = stripNonValidXMLCharacters(str);

            Assert.assertNotEquals(str, output);
        }
    }
}
