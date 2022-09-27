<!-- Brief description of your project. For example, What is it? How is the source-code organized? -->
# JPass
JPass is a simple, small, portable password manager application with strong encryption. It allows you to store user names, passwords, URLs and generic notes in an encrypted file protected by one master password.

Features:

- Strong encryption - AES-256-CBC algorithm (SHA-256 is used as password hash)
- Portable - single jar file which can be carried on a USB stick
- Built-in random password generator
- Organize all your user name, password, URL and notes information in one file
- Data import/export in XML format

![JPass](https://raw.githubusercontent.com/gaborbata/jpass/master/resources/jpass-capture.png)

## How to use


## Source code structure

The code repository has 3 folders (one of which - `docs` was added by us). In the `resources` folder, there are many `.png`, `.svg` and `.ico` files corresponding mostly to different JPass logotype dimensions and colors, as well as some other images regarding screenshots of the application itself. The `src` folder holds two sub-folders: `test` where some JUnit (not sure???) tests can be found and `main` where the code itself is present (`java` folder).

---

## Static testing

Static testing is a way to assess whether the software was build right or not, i.e., if the system actually behaves according to the specification provided. It is utilized in order to check for existing faults in the software but, as the name implies, without executing the source code, being, for this reason, a key instrument in producing better quality software. 
There are 2 types of static testing techniques: the reviews (manual procedures) and the ones in focus in this assignment: automated analysis tools.

## Chosen static testing tools

For this assignment we picked Checkstyle and Spotbugs as the static testing tools.

TODO after:
- how they were configured for the project: reason for enabling/disabling configurations

## Reports

### Checkstyle

TODO

### Spotbugs

TODO

### Selected bugs

TODO: 5 bugs x 2 tools.
- brief description of each.
- fixes with before and after.
