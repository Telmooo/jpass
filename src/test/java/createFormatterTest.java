import static jpass.util.DateUtils.createFormatter;

import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;

public class createFormatterTest {

    @Test
    public void testingNull() {

        DateTimeFormatter dtf = createFormatter(null);

        Assert.assertTrue(dtf.getClass() == DateTimeFormatter.class);
        Assert.assertEquals(DateTimeFormatter.ISO_DATE, dtf);
    }

    @Test
    public void testingEmpty() {
        String input = "";

        DateTimeFormatter dtf = createFormatter(input);

        Assert.assertTrue(dtf.getClass() == DateTimeFormatter.class);
    }

    @Test
    public void testingConstants() {
        String[] arr = {
                "yyyy MM dd",
        };


        for (String format : arr) {
            DateTimeFormatter dtf = createFormatter(format);


        }
    }
}
