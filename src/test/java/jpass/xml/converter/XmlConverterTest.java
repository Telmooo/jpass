package jpass.xml.converter;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;

public class XmlConverterTest {

    private PasswordManagerUtil manager;
    private File xmlFile;

    private XmlMapper mapper;
    private XmlConverter<PasswordManagerUtil> converter;

    @BeforeEach
    public void setUp() throws IOException {
        manager = new PasswordManagerUtil("master");

        xmlFile = File.createTempFile("xml-", Long.toString(System.nanoTime()));

        converter = new XmlConverter<>(PasswordManagerUtil.class);

        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        mapper = new XmlMapper(module);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
    }

    @AfterEach
    public void cleanUp() {
        xmlFile.delete();
    }

    @Test
    public void testXmlConverterRead() throws IOException {
        mapper.writeValue(xmlFile, manager);
        InputStream stream = new BufferedInputStream(Files.newInputStream(xmlFile.toPath()));

        PasswordManagerUtil managerRead = converter.read(stream);

        Assertions.assertEquals(manager.getMasterPassword(), managerRead.getMasterPassword());
    }

    @Test
    public void testXmlConverterWrite() throws IOException {
        OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(xmlFile.toPath()));

        converter.write(manager, outputStream);

        InputStream inputStream = new BufferedInputStream(Files.newInputStream(xmlFile.toPath()));

        PasswordManagerUtil managerRead = mapper.readValue(inputStream, PasswordManagerUtil.class);

        Assertions.assertEquals(manager.getMasterPassword(), managerRead.getMasterPassword());
    }

}