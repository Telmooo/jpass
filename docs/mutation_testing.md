# Mutation Testing

## jpass.util.ClipboardUtils

Despite the tests passing when running `mvn test`, Pitest outputs failures. This lead to the class being ignored from mutation testing.

## jpass.util.Configuration



## jpass.xml.converter (`XMLConverter.java`)

This class had a single mutant surviving: removed call to ``module.setDefaultUseWrapper(false)``.
To fix this, the auxiliary class `PasswordManagerUtil` that was created for testing purposes was modified to include an extra property - an `ArrayList` -, to properly take into account the wrapper used by the `JacksonXmlModule` object. According to the [documentation](https://micronaut-projects.github.io/micronaut-jackson-xml/latest/api/io/micronaut/xml/jackson/JacksonXmlConfiguration.html), the method call in question affects properties that are Lists or arrays possibilities which were not considered in testing previously.

`````diff
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
`````

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