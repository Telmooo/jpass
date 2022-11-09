package jpass.xml.bind;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntriesTest {

    Entries entries;

    @BeforeEach
    public void setUp() {
        entries = new Entries();
    }

    @Test
    void testEmptyGetEntry() {
        Assertions.assertEquals(0, entries.getEntry().size());
    }

    @Test
    void testNonEmptyGetEntry() {
        entries.getEntry().add(new Entry());
        Assertions.assertEquals(1, entries.getEntry().size());
    }
}
