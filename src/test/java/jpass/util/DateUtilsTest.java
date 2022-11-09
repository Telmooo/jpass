package jpass.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;

import static jpass.util.DateUtils.createFormatter;
import static jpass.util.DateUtils.formatIsoDateTime;

class DateUtilsTest {

    @Test
    public void testNullCreateFormatter() {

        DateTimeFormatter dtf = createFormatter(null);

        Assertions.assertEquals(DateTimeFormatter.ISO_DATE, dtf);
    }

    @Test
    public void testIllegalArgumentCreateFormatter() {
        DateTimeFormatter dtf = createFormatter("aaaaaa");

        Assertions.assertEquals(DateTimeFormatter.ISO_DATE, dtf);
    }

    @Test
    public void testEmptyCreateFormatter() {
        LocalDateTime l = LocalDateTime.now();

        DateTimeFormatter dtf = createFormatter("");
        String expectedStr = l.format(new DateTimeFormatterBuilder().toFormatter());
        String outputStr = l.format(dtf);

        Assertions.assertEquals(expectedStr, outputStr);
    }

    @Test
    public void testValidDateCreateFormatter() {
        String input = "yyyyMMdd";
        LocalDate l = LocalDate.now();

        DateTimeFormatter dtf = createFormatter(input);
        String expectedStr = l.format(DateTimeFormatter.BASIC_ISO_DATE);
        String outputStr = l.format(dtf);

        Assertions.assertEquals(expectedStr, outputStr);
    }

    @Test
    public void testValidTimeCreateFormatter() {
        String input = "HH:mm:ss";
        LocalTime l = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);

        DateTimeFormatter dtf = createFormatter(input);
        String expectedStr = l.format(DateTimeFormatter.ISO_LOCAL_TIME);
        String outputStr = l.format(dtf);

        Assertions.assertEquals(expectedStr, outputStr);
    }

    @Test
    public void testNullDateStringFormatIsoDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        Assertions.assertDoesNotThrow( () -> {
            formatIsoDateTime(null, formatter);
        });
    }

    @Test
    public void testNullFormatterFormatIsoDateTime() {
        String output = formatIsoDateTime("2018-07-14T17:45:55.9483536", null);

        Assertions.assertEquals("2018-07-14T17:45:55", output);
    }

    @Test
    public void testDateTimeParseExceptionFormatIsoDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String input = "2018-07-14";

        String output = formatIsoDateTime(input, formatter);

        Assertions.assertEquals("1970-01-01", output);
    }

    @Test
    public void testValidArgumentsFormatIsoDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String input = "2018-07-14T17:45:55.9483536";

        String output = formatIsoDateTime(input, formatter);

        Assertions.assertEquals("2018-07-14", output);
    }

    @Test
    public void testFormatIsoDateTimeUnixTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String input = "1667996411000";

        Assertions.assertEquals("2022-11-09T12:20:11", formatIsoDateTime(input, formatter));
    }
}