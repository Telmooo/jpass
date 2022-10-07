import static jpass.util.DateUtils.createFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class createFormatterTest {

    @Test
    public void testNullPointer() {

        DateTimeFormatter dtf = createFormatter(null);

        Assertions.assertEquals(DateTimeFormatter.ISO_DATE, dtf);
    }

    @Test
    public void testIllegalArgument() {
        DateTimeFormatter dtf = createFormatter("aaaaaa");

        Assertions.assertEquals(DateTimeFormatter.ISO_DATE, dtf);
    }

    @Test
    public void testEmpty() {
        LocalDateTime l = LocalDateTime.now();

        DateTimeFormatter dtf = createFormatter("");
        String expectedStr = l.format(new DateTimeFormatterBuilder().toFormatter());
        String outputStr = l.format(dtf);

        Assertions.assertEquals(expectedStr, outputStr);
    }

    @Test
    public void testValidDate() {
        String input = "yyyyMMdd";
        LocalDate l = LocalDate.now();

        DateTimeFormatter dtf = createFormatter(input);
        String expectedStr = l.format(DateTimeFormatter.BASIC_ISO_DATE);
        String outputStr = l.format(dtf);

        Assertions.assertEquals(expectedStr, outputStr);
    }

    @Test
    public void testValidTime() {
        String input = "HH:mm:ss";
        LocalTime l = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);

        DateTimeFormatter dtf = createFormatter(input);
        String expectedStr = l.format(DateTimeFormatter.ISO_LOCAL_TIME);
        String outputStr = l.format(dtf);

        Assertions.assertEquals(expectedStr, outputStr);
    }
}
