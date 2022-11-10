package jpass.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jpass.xml.bind.Entries;
import org.junit.jupiter.api.*;

public class EntriesRepositoryTest {

    private final String fileName = "testing.jpass";
    private final byte[] password = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8".getBytes();

    @Test
    public void testNewInstanceNoEncryption() {
        EntriesRepository entriesRepo = EntriesRepository.newInstance(fileName);

        Assertions.assertNotNull(entriesRepo);
    }

    @Test
    public void testNewInstanceEncryption() {
        EntriesRepository entriesRepo = EntriesRepository.newInstance(fileName, password);

        Assertions.assertNotNull(entriesRepo);
    }

    @Nested
    @DisplayName("Read/Write-related tests")
    class fileIO {
        File tempFile;

        @BeforeEach
        public void setUp() {
            try {
                tempFile = File.createTempFile("testFile", ".jpass");
                System.out.println("Temp file : " + tempFile.getAbsolutePath());

                String absolutePath = tempFile.getAbsolutePath();
                String tempFilePath = absolutePath
                        .substring(0, absolutePath.lastIndexOf(File.separator));

                System.out.println("Temp file path : " + tempFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Test
        public void testReadDocumentNull() {
            EntriesRepository entriesRepository = EntriesRepository.newInstance(null);
            Assertions.assertThrows(DocumentProcessException.class, () -> entriesRepository.readDocument());
        }

        @Test
        public void testReadDocumentNotFound() {
            EntriesRepository entriesRepository = EntriesRepository.newInstance("unknown.jpass");
            Assertions.assertThrows(FileNotFoundException.class, () -> entriesRepository.readDocument());
        }

        @Test
        public void testReadDocumentFound() {
            EntriesRepository entriesRepository = EntriesRepository.newInstance(tempFile.getAbsolutePath());

            XmlMapper xmlMap = new XmlMapper();
            Assertions.assertDoesNotThrow(() -> xmlMap.writeValue(tempFile, new Entries()));

            Entries entries;
            try {
                entries = entriesRepository.readDocument();
                Assertions.assertEquals(1, entries.getEntry().size());
            } catch (Exception e) {
                Assertions.fail("Unexpected exception thrown");
            }
        }

        @Test
        public void testWriteDocumentNullKey() {
            EntriesRepository entriesRepository = EntriesRepository.newInstance(tempFile.getAbsolutePath(), null);
            Entries entries = new Entries();

            Assertions.assertDoesNotThrow(() -> entriesRepository.writeDocument(entries));
        }

        @Test
        public void testWriteDocumentNotNullKey() {
            EntriesRepository entriesRepository = EntriesRepository.newInstance(tempFile.getAbsolutePath(), password);
            Entries entries = new Entries();

            Assertions.assertDoesNotThrow(() -> entriesRepository.writeDocument(entries));
        }

        @Test
        public void testWriteDocumentNull() {
            EntriesRepository entriesRepository = EntriesRepository.newInstance(null);
            Entries entries = new Entries();

            Assertions.assertThrows(DocumentProcessException.class, () -> entriesRepository.writeDocument(entries));
        }
    }
}
