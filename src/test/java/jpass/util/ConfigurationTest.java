package jpass.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class ConfigurationTest {

    private File filePath;
    private Properties prevProperties;

    private Configuration configuration;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException {
        File configurationFolderPath = new File(Configuration.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()).getParentFile();


        filePath = new File(configurationFolderPath, "jpass.properties");

        if (filePath.exists() && filePath.isFile()) {
            prevProperties = new Properties();
            InputStream is = Files.newInputStream(filePath.toPath());
            prevProperties.load(is);
            is.close();
        } else {
            filePath.createNewFile();
        }

        Properties properties = new Properties();
        properties.setProperty("test.list", "2,3");
        properties.setProperty("test.boolean", "true");
        properties.setProperty("test.length", "20");
        properties.setProperty("test.pass", "jojo");

        OutputStream outputStream = Files.newOutputStream(filePath.toPath(), new StandardOpenOption[]{TRUNCATE_EXISTING});

        properties.store(outputStream, "Testing Property File");

        configuration = Configuration.getInstance();
    }

    @AfterEach
    public void cleanUp() throws IOException {
        if (prevProperties == null) {
            filePath.delete();
        } else {
            OutputStream outputStream = Files.newOutputStream(filePath.toPath(), new StandardOpenOption[]{TRUNCATE_EXISTING});

            prevProperties.store(outputStream, "");
        }
    }

    @Test
    public void testConfigurationIs() {
        Assertions.assertTrue(configuration.is("test.boolean", false));
    }

    @Test
    public void testConfigurationIsNoExists() {
        Assertions.assertTrue(configuration.is("nonexistantkey", true));
    }

    @Test
    public void testConfigurationIsExistsNoBoolean() {
        Assertions.assertFalse(configuration.is("test.list", false));
    }

    @Test
    public void testConfigurationGetInteger() {
        Assertions.assertEquals(20, configuration.getInteger("test.length", 10));
    }

    @Test
    public void testConfigurationGetIntegerDefault() {
        Assertions.assertEquals(10, configuration.getInteger("test.boolean", 10));
    }

    @Test
    public void testConfigurationGetArray() {
        String[] expected = new String[] { "2", "3" };
        String[] defaultValue = new String[] { "default", "array" };

        Assertions.assertArrayEquals(expected, configuration.getArray("test.list", defaultValue));
    }

    @Test
    public void testConfigurationGetArrayDefault() {
        String[] defaultValue = new String[] { "default", "array" };

        Assertions.assertArrayEquals(defaultValue, configuration.getArray("test.error", defaultValue));
    }

    @Test
    public void testConfigurationGet() {
        Assertions.assertEquals("jojo", configuration.get("test.pass", "dio"));
    }
}