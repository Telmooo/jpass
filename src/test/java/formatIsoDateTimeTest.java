import static jpass.util.DateUtils.formatIsoDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class formatIsoDateTimeTest {

    @Test
    public void testNullDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String input = LocalDateTime.now().format(formatter);

        String expected = formatIsoDateTime(input, formatter);
        String output = formatIsoDateTime(null, formatter);

        Assertions.assertEquals(expected, output);
    }

    @Test
    public void testNullFormatter() {
        Assertions.assertThrows(NullPointerException.class,
                () -> formatIsoDateTime("2020-10-09", null)
        );
    }

    @Test
    public void testValidArguments() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String input = LocalDateTime.now().format(formatter);

        String expected = formatIsoDateTime(input, formatter);
        String output = formatIsoDateTime(null, formatter);

        Assertions.assertEquals(expected, output);
    }

    // excption para o try catch
}
