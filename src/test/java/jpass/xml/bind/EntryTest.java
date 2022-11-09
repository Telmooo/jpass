package jpass.xml.bind;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntryTest {

    Entry entry;
    String last_modification_expected;

    @BeforeEach
    public void setUp() {
        entry = new Entry();
        last_modification_expected = LocalDateTime.now()
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Test
    public void testTitleGetter() {
        String expected = "title_get";
        entry.title = expected;

        Assertions.assertEquals(expected, entry.getTitle());
    }

    @Test
    public void testTitleSetter() {
        String expected = "title_set";
        entry.setTitle(expected);

        Assertions.assertEquals(expected, entry.title);
    }

    @Test
    public void testURLGetter() {
        String expected = "url_get";
        entry.url = expected;

        Assertions.assertEquals(expected, entry.getUrl());
    }

    @Test
    public void testURLSetter() {
        String expected = "url_set";
        entry.setUrl(expected);

        Assertions.assertEquals(expected, entry.url);
    }

    @Test
    public void testUserGetter() {
        String expected = "user_get";
        entry.user = expected;

        Assertions.assertEquals(expected, entry.getUser());
    }

    @Test
    public void testUserSetter() {
        String expected = "user_set";
        entry.setUser(expected);

        Assertions.assertEquals(expected, entry.user);
    }

    @Test
    public void testPasswordGetter() {
        String expected = "password_get";
        entry.password = expected;

        Assertions.assertEquals(expected, entry.getPassword());
    }

    @Test
    public void testPasswordSetter() {
        String expected = "password_set";
        entry.setPassword(expected);

        Assertions.assertEquals(expected, entry.password);
    }

    @Test
    public void testNotesGetter() {
        String expected = "notes_get";
        entry.notes = expected;

        Assertions.assertEquals(expected, entry.getNotes());
    }

    @Test
    public void testNotesSetter() {
        String expected = "notes_set";
        entry.setNotes(expected);

        Assertions.assertEquals(expected, entry.notes);
    }

    @Test
    public void testCreationDateGetter() {
        String expected = "creation_date_get";
        entry.creationDate = expected;

        Assertions.assertEquals(expected, entry.getCreationDate());
    }

    @Test
    public void testCreationDateSetter() {
        String expected = "creation_date_set";
        entry.setCreationDate(expected);

        Assertions.assertEquals(expected, entry.creationDate);
    }

    @Test
    public void testLastModificationGetter() {
        Assertions.assertEquals(last_modification_expected, entry.getLastModification());
    }
}
