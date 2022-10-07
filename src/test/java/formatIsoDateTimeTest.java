import static jpass.util.DateUtils.formatIsoDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class formatIsoDateTimeTest {

    @Test
    public void testNullDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        Assertions.assertDoesNotThrow( () -> {
            formatIsoDateTime(null, formatter);
        });
    }

    @Test
    public void testNullFormatter() {

        String output = formatIsoDateTime("2020-10-09", null);
        String expected = formatIsoDateTime("2020-10-09", DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Assertions.assertEquals(expected, output);
    }

    @Test
    public void testDateTimeParseException() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String input = "2018-07-14";

        String output = formatIsoDateTime(input, formatter);

        Assertions.assertEquals("1970-01-01", output);
    }

    @Test
    public void testValidArguments() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String input = "2018-07-14T17:45:55.9483536";

        String output = formatIsoDateTime(input, formatter);

        Assertions.assertEquals("2018-07-14", output);
    }

}
