# JPass
JPass is a simple, small, portable password manager application with strong encryption. It allows you to store user names, passwords, URLs and generic notes in an encrypted file protected by one master password.

Features:

- Strong encryption - AES-256-CBC algorithm (SHA-256 is used as password hash)
- Portable - single jar file which can be carried on a USB stick
- Built-in random password generator
- Organize all your user name, password, URL and notes information in one file
- Data import/export in XML format

![JPass](https://raw.githubusercontent.com/gaborbata/jpass/master/resources/jpass-capture.png)

## Source code structure

In the `resources` folder, various assets files are present corresponding mostly to different JPass logotype dimensions and colours, as well as some other images regarding screenshots of the application itself. The `src` folder holds two sub-folders: `test` where tests are located and `main` where the code itself is present (`java` folder).

### Package Structure
#### Crypt (jpass.crypt)
Package responsible for encryption of blocks being written and read by the program.
**Aes256** implements the [**AES**](https://en.wikipedia.org/wiki/Advanced_Encryption_Standard) cipher algorithm with key size of 256 bits.
**Cbc** implements [**Cipher Block Chaining**](https://www.techtarget.com/searchsecurity/definition/cipher-block-chaining) to operate on blocks written and read by the program. It uses AES256 as its cipher.

Contains a subpack `jpass.crypt.io` that implements encrypted streams for input and output of data.

#### Data (jpass.data)
Package implements the data model of the application that is responsible for storing entries and information about the configuration of the save file (name, password).

#### UI (jpass.ui)
Package that contains all classes responsible for handling the rendering of the UI and handling user actions on the UI.

#### Util (jpass.util)
Compilation of utility methods.
- `ClipboardUtils` - Functions to handle content of system clipboard;
- `Configuration` - Handles configuration for JPass application (configuration file located in `src/main/config`;
- `CryptUtils` - Provides methods to create RNG and hashing methods;
- `DateUtils` - Handles parsing of dates;
- `SpringUtilities` - Handles layout creation for Spring.
- `StringUtils` - Handles parsing of strings.

#### XML (jpass.xml)
Contains two packages:
- `jpass.xml.bind`: implements the data representation of each entry for the password manager;
- `jpass.xml.converter`: implements I/O operations for XML format.

### JPass (jpass.JPass)
Program main entry point. Initializes the UI and its configurations.

---

## Static testing

Static testing is a testing technique used to perform an analysis on a software application without executing it. It exists in two types, code reviews (manual procedures) and automated analysis via usage of tools such as Checkstyle and Spotbugs, for example.

The main concern of static testing is to detect faults, suspicious code or design that may result in faults, inconsistencies in the software and improve maintainability of code and design of the software application.

While simple, static testing helps with the early detection of faults and thus allows developers to build software with higher quality, and waste less resources later on fixing faults that could be prevented.

## Chosen static testing tools

For this assignment, we picked Checkstyle and Spotbugs as the static testing tools.

## Reports

### Checkstyle
Go to [report](checkstyle-report.md).

### Spotbugs
Go to [report](spotbugs-report.md).
