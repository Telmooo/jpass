# Mutation Testing

## jpass.util.ClipboardUtils

Despite the tests passing when running `mvn test`, Pitest outputs failures. This lead to the class being ignored from mutation testing.

However, the inner class within `ClipboardUtils`, `EmptyClipboardContent`, had surviving mutants due to no coverage. To fix this, the following tests were implemented:
```diff
+ @Test
+ public void testEmptyClipboardObject() {
+    ClipboardUtils.EmptyClipboardContent emptyClipboardContent = new ClipboardUtils.EmptyClipboardContent();
+
+    DataFlavor dataFlavor = DataFlavor.stringFlavor;
+
+    Assertions.assertFalse(emptyClipboardContent.isDataFlavorSupported(dataFlavor));
+    Assertions.assertArrayEquals(new DataFlavor[] { }, emptyClipboardContent.getTransferDataFlavors());
+    Assertions.assertThrows(UnsupportedFlavorException.class, () -> emptyClipboardContent.getTransferData(dataFlavor));
+ }
```
Due to the simplicity of the code to be tested, just a simple test to cover the lines were enough to kill the mutants.

## jpass.util.Configuration

This class had 4 mutants that survived, however, only two of these was a valid mutant survival. The image below showcases the four mutants that survived not by lack of testing, but rather due to Pitest fault. The mutants are, in order:
1. Negated Conditional on `filePath.exists()`
2. Negated Conditional on `filePath.isFile()`
3. Removed call on `properties.load(is)`
4. replace return with null on `return configurationFolderPath`

![Survived Mutants](assets/ConfigurationSurvivedMutants.png)

The tests developed should cover these mutations, since, for example, by negating the conditional, the properties would not be loaded, and therefore tests checking if the properties are correctly loaded would fail. However, Pitest has an issue when isolating test cases and mutations with Singleton-type classes, like `Configuration`, and falsely reports this mutants due to previous state being kept between tests/mutations.

As a workaround to address this issue, we used Reflections at the setup method of this class to clear the Configuration instance:
```diff
properties.store(outputStream, "Testing Property File");

+ Field instanceField = Configuration.class.getDeclaredField("INSTANCE");
+ instanceField.setAccessible(true);
+ instanceField.set(configuration, null);

configuration = Configuration.getInstance();
```

The remaining two mutants that survived were:

- Remove call on `is.close()`

To address this mutant, we added a test that attempted to delete the file after creating an instance of the Configuration, as we observed the file couldn't be deleted if the input stream wasn't closed. However, the garbage collector should've closed the input stream regardless after the method ended, as it was observed on another situation (see [EntriesRepository](#jpassdataentriesrepository)). So this mutant might be a case of an equivalent mutant.

- Negate conditional on `if (INSTANCE == null)`

To address this mutant, we added a test to verify if the singleton is working correctly, since this if checks if the instance already exists, and if so, return the previously created instance.
```diff
+ @Test
+ public void testConfigurationSingleton() throws IOException {
+     Properties properties = new Properties();
+     properties.setProperty("test.pass", "notjojo");
+
+    OutputStream outputStream = Files.newOutputStream(filePath.toPath(), new StandardOpenOption[]{TRUNCATE_EXISTING});
+
+    properties.store(outputStream, "Testing Property File");
+
+    Configuration configuration_singleton = Configuration.getInstance();
+
+    Assertions.assertEquals(configuration.get("test.pass", "undefined"), configuration_singleton.get("test.pass", "undefined2"));
+}
```

## jpass.data.EntriesRepository



## jpass.xml.converter (`XMLConverter.java`)

This class had a single mutant surviving: removed call to ``module.setDefaultUseWrapper(false)``.
To fix this, the auxiliary class `PasswordManagerUtil` that was created for testing purposes was modified to include an extra property - an `ArrayList` -, to properly take into account the wrapper used by the `JacksonXmlModule` object. According to the [documentation](https://micronaut-projects.github.io/micronaut-jackson-xml/latest/api/io/micronaut/xml/jackson/JacksonXmlConfiguration.html), the method call in question affects properties that are Lists or arrays possibilities which were not considered in testing previously.

````diff
    protected String masterPassword;
+   protected ArrayList<String> passwordList;

(...)

public class PasswordManagerUtil {
    @JacksonXmlProperty(localName = "master-pass")
    protected String masterPassword;
+   protected ArrayList<String> passwordList;

    @JsonCreator
-   public PasswordManagerUtil(@JsonProperty("master-pass") String masterPassword) {
+   public PasswordManagerUtil(@JsonProperty("master-pass") String masterPassword, @JsonProperty("pass-list") ArrayList<String> passwordList) {
        this.masterPassword = masterPassword;
+       this.passwordList = passwordList;
    }

(...)

    @JsonGetter(value = "pass-list")
    public ArrayList<String> getPasswordList() { return this.passwordList; }
````

By consequence, the `setUp` method in the `XmlConverterTest` class was modified to accommodate the changes in `PasswordManagerUtil`.

```diff
    @BeforeEach
    public void setUp() throws IOException {
-        manager = new PasswordManagerUtil("master");
+       ArrayList<String> passList = new ArrayList<>(Arrays.asList("pass1", "pass2"));
+       manager = new PasswordManagerUtil("master", passList);

        xmlFile = File.createTempFile("xml-", Long.toString(System.nanoTime()));
```

After these changes, the class ``XMLConverter`` and, by extension, the `jpass.xml.converter` had a line and mutation coverage, and test strength of 100%.
