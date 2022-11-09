package jpass.data;

import java.util.Arrays;
import java.util.List;

import jpass.xml.bind.Entries;
import jpass.xml.bind.Entry;
import org.junit.jupiter.api.*;

public class DataModelTest {

    static DataModel dataModel;

    @BeforeAll
    public static void init() {
        dataModel = DataModel.getInstance();
    }

    @BeforeEach
    public void setUp() {
        dataModel.clear();
    }

    @Test
    public void testNullGetInstance() {
        Assertions.assertNotNull(dataModel);
    }

    @Test
    public void testGetEntries() {
        Assertions.assertNotNull(dataModel.getEntries());
    }

    @Test
    public void testSetEntries() {
        Entries entries = new Entries();
        entries.getEntry().add(new Entry());

        dataModel.setEntries(entries);

        Assertions.assertEquals(1, dataModel.getEntries().getEntry().size());
    }

    @Test
    public void testGetFileName() {
        Assertions.assertNull(dataModel.getFileName());
    }

    @Test
    public void testSetFileName() {
        dataModel.setFileName("FileName Updated");
        Assertions.assertEquals("FileName Updated", dataModel.getFileName());
    }

    @Test
    public void testIsModified() {
        Assertions.assertFalse(dataModel.isModified());
    }

    @Test
    public void testSetModified() {
        dataModel.setModified(true);
        Assertions.assertTrue(dataModel.isModified());
    }

    @Test
    public void testGetPassword() {
        Assertions.assertThrows(NullPointerException.class, () -> dataModel.getPassword());
    }

    @Test
    public void testSetPassword() {
        byte[] expected = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8".getBytes();
        dataModel.setPassword(expected);

        Assertions.assertArrayEquals(expected, dataModel.getPassword());
    }

    @Test
    public void testClear() {
        dataModel.setFileName("new File");
        Assertions.assertNotNull(dataModel.getFileName());
        dataModel.clear();
        Assertions.assertNull(dataModel.getFileName());
    }

    @Nested
    @DisplayName("Title-related tests")
    class Titles {
        @BeforeEach
        public void setUp() {
            Entries entries = new Entries();                    // create Entries object
            Entry entry1 = new Entry(), entry2 = new Entry();   // create Entry objects
            entry1.setTitle("Title 1");                         // change Entry properties
            entry2.setTitle("Title 2");
            entries.getEntry().add(entry1);                     // add Entry objects to Entries
            entries.getEntry().add(entry2);

            dataModel.setEntries(entries);
        }

        @Test
        public void testGetTitles() {
            List<String> expected = Arrays.asList("Title 1", "Title 2");

            Assertions.assertArrayEquals(expected.toArray(), dataModel.getTitles().toArray());
        }

        @Test
        public void testGetEntryIndexByTitle() {
            Assertions.assertEquals(1, dataModel.getEntryIndexByTitle("Title 2"));
        }

        @Test
        public void testGetEntryByTitleFound() {
            Assertions.assertEquals("Title 1", dataModel.getEntryByTitle("Title 1").getTitle());
        }

        @Test
        public void testGetEntryByTitleNotFound() {
            Assertions.assertNull(dataModel.getEntryByTitle("Title 3"));
        }
    }


}
