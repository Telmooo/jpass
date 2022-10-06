import static jpass.util.StringUtils.stripNonValidXMLCharacters;

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
            System.out.println(alphaNumeric);
            Assert.assertEquals(alphaNumeric, output);
        }
    }

    @Test
    public void idk() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        String output = stripNonValidXMLCharacters(generatedString);

        Assert.assertEquals(generatedString, output);
    }
}
