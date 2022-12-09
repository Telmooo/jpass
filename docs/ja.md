### jpass.data

#### DataModel.java
- in method ``getInstance`` , ``if (INSTANCE == null)`` is changed, and it doesn't get detected.
- in method ``clear``, ``this.entries.getEntry().clear();`` is omitted but the mutant survives

#### EntriesRepository.java
- ``.close()`` calls for both input and output streams are omitted but not detected.

### jpass.util

#### ClipboardUtils.java
- no coverage

#### Configuration.java
- might be "fixable"

#### CryptUtils.java
- 2 mutations survived
- `md.reset()` is responsible for one of the unkilled mutants (not sure if it is possible to test it)
- "negate conditional" in the for loop is the cause for the second unkilled mutant (timed out)

#### DateUtils.java
- all mutants killed
- (line coverage not 100% due to a private empty constructor)
```
private DateUtils() { }
```

#### StringUtils.java
- all mutants killed
- (line coverage not 100% due to a private empty constructor)
```
private StringUtils() {
  // utility class
}
```

----

### jpass.xml.bind
- all mutants killed

----

### jpass.xml.converter

#### XmlConverter.java

Survived mutants:
- removed call to com/fasterxml/jackson/dataformat/xml/JacksonXmlModule::setDefaultUseWrapper → SURVIVED
  https://fasterxml.github.io/jackson-dataformat-xml/javadoc/2.9/com/fasterxml/jackson/dataformat/xml/JacksonXmlModule.html#setDefaultUseWrapper(boolean)
- don't know how to kill the mutant










