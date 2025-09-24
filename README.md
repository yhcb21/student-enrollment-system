# Student Course & Enrollment Management System

This project is a comprehensive, command-line-based student management system developed in Java. It demonstrates a wide range of core Java features, from fundamental syntax and object-oriented principles to advanced topics like the Stream API, NIO.2 for file I/O, and modern design patterns. The application is built with a clear, package-based architecture to ensure separation of concerns.

## How to Run

### Prerequisites
* **Java Development Kit (JDK):** Version 11 or higher.
* **Git:** To clone the repository.
* **(Optional) IDE:** Eclipse or IntelliJ IDEA.

### 1. From the Command Line
1.  **Clone the repository:**
```bash
    git clone [https://github.com/your-username/student-enrollment-system.git](https://github.com/yourusername/student-enrollment-system.git)
    cd student-enrollment-system
	```
3.  **Create the data directory:**
    ```bash
    mkdir data
    ```
    Create `students.csv` and `courses.csv` inside the `data` folder and populate them with the sample data.

4.  **Compile all source files:**
    ```bash
    # Create a directory for compiled classes
    mkdir bin

    # Compile (for macOS/Linux)
    javac -d bin $(find src -name "*.java")

    # Compile (for Windows)
    javac -d bin src\edu\ccrm\cli\*.java src\edu\ccrm\config\*.java src\edu\ccrm\domain\*.java src\edu\ccrm\exception\*.java src\edu\ccrm\io\*.java src\edu\ccrm\service\*.java src\edu\ccrm\util\*.java
    ```

5.  **Run the application:**
    ```bash
    java -cp bin edu.ccrm.cli.CliManager
    ```
---
### 2. From an IDE (Eclipse/IntelliJ)
1.  Clone the repository.
2.  Import the project into your IDE (e.g., in Eclipse, `File > Import > Maven > Existing Maven Projects`).
3.  Create the `data` folder in the project's root directory, alongside `src`. Add the sample `students.csv` and `courses.csv` files.
4.  Locate the `CliManager.java` file in `src/edu/ccrm/cli/`.
5.  Right-click the file and select `Run As > Java Application`.

## Java Platform Overview

### Evolution of Java ☕
* **1995:** Java 1.0 is released by Sun Microsystems with the motto "Write Once, Run Anywhere."
* **1998:** J2SE 1.2 (Java 2) is released, introducing the Swing GUI toolkit and Collections framework.
* **2004:** J2SE 5.0 (Tiger) introduces major language features like Generics, Enums, Autoboxing, and Annotations.
* **2011:** Oracle acquires Sun. Java SE 7 is released with features like the Fork/Join framework and `try-with-resources`.
* **2014:** Java SE 8 marks a revolutionary release with Lambda Expressions, the Stream API, and a new Date/Time API.
* **2017-Present:** Java moves to a faster, 6-month release cycle starting with Java 9, introducing features like the Module System, `var` for local variables (Java 10), and enhanced `switch` expressions and Text Blocks (Java 14+).

### Java ME vs SE vs EE
| Feature           | Java ME (Micro Edition)                          | Java SE (Standard Edition)                            | Java EE (Enterprise Edition)                          |
| ----------------- | ------------------------------------------------ | ----------------------------------------------------- | ----------------------------------------------------- |
| **Purpose** | Resource-constrained devices (e.g., embedded systems) | General-purpose desktop, server, and console apps     | Large-scale, distributed, network-centric applications |
| **Core API** | A small subset of the Java SE API                | The core Java platform (JVM, Collections, I/O, etc.) | Built on top of Java SE, adding more APIs           |
| **Key Libraries** | `CLDC`, `MIDP` for mobile profiles               | `java.lang`, `java.util`, `java.io`, Swing, JavaFX | Servlets, JSP, EJB, JPA, JMS, Web Services (JAX-RS)   |
| **Example App** | An application on a smart card or older feature phone. | This project, a desktop calculator, Minecraft.        | A large banking portal or a multi-tier e-commerce site. |

### JDK vs JRE vs JVM
* **JVM (Java Virtual Machine):** The abstract machine that executes Java bytecode. It's the "Run Anywhere" part, as JVMs are platform-specific (Windows, macOS, Linux) but all run the same universal bytecode. It handles memory management, garbage collection, and security.
* **JRE (Java Runtime Environment):** The software package that provides the JVM and the necessary libraries to *run* Java applications. If you only want to run a Java program, you only need the JRE.
* **JDK (Java Development Kit):** The full development kit for Java programmers. It includes everything the JRE has, plus development tools like the **compiler** (`javac`), **debugger** (`jdb`), and documentation generator (`javadoc`). You need the JDK to *write* and compile Java code.

**Interaction:** A developer writes Java code (`.java` files) and uses the **JDK** to compile it into bytecode (`.class` files). To run the program, the **JRE** is launched, which starts up a **JVM** instance to execute that bytecode.


## Installation & Setup Guide

### Installing Java JDK on Windows
1.  **Download the JDK:** Go to the [Oracle Java Downloads page](https://www.oracle.com/java/technologies/downloads/) and download the Windows x64 Installer for a recent JDK version (e.g., JDK 21).
2.  **Run the Installer:** Double-click the downloaded `.exe` file and follow the on-screen instructions. It's usually a straightforward "Next, Next, Finish" process.
3.  **Configure Environment Variables:**
    * Search for "Edit the system environment variables" in the Start Menu and open it.
    * Click the "Environment Variables..." button.
    * Under "System variables," find the `Path` variable, select it, and click "Edit...".
    * Click "New" and add the path to your JDK's `bin` folder. This is typically `C:\Program Files\Java\jdk-21\bin`.
    * (Optional but recommended) Create a new system variable called `JAVA_HOME` and set its value to the JDK installation directory (e.g., `C:\Program Files\Java\jdk-21`).
4.  **Verify the Installation:** Open a new Command Prompt and run `java -version`. You should see the version of the JDK you just installed.

### Using Eclipse IDE
1.  **Create a New Project:**
    * Go to `File > New > Java Project`.
    * Give your project a name (e.g., `StudentEnrollmentSystem`).
    * Ensure the "Use an execution environment JRE" is set to your installed JDK version. Click Finish.
2.  **Set up Run Configuration:**
    * After adding the code, right-click on `CliManager.java` in the Package Explorer.
    * Select `Run As > Run Configurations...`.
    * A configuration for `CliManager` should already exist. You can review the settings in the "Main" and "Arguments" tabs if needed.
    * Click **Run**.
## Syllabus Topic to Code Mapping
| Syllabus Topic                   | File/Class/Method Where Demonstrated                                |
| -------------------------------- | ------------------------------------------------------------------- |
| **Packages & `main` class** | Project structure (`edu.ccrm.*`), `CliManager.main()`               |
| **Primitives, Objects, Operators** | Used throughout all classes (e.g., `int`, `String`, arithmetic in `calculateGpa`) |
| **Decision Structures (if/switch)**| `CliManager.manageStudents()`, `Grade.fromMarks()`, `CliManager.main()` loop switch |
| **Loops (for, while, enhanced)** | `CliManager.main()` (`while`), `EnrollmentService` (`enhanced for`)   |
| **Jump Control (break, continue)** | `CliManager.main()` (labeled `break OUTER_LOOP`)                     |
| **Arrays & `Arrays` class** | `CliManager.manageStudents()` (`Arrays.toString` on enum values)   |
| **Strings & common methods** | `ImportExportService` (`split`, `join`), `CourseCode` (`replaceAll`) |
| **OOP - Encapsulation** | `Person.java`, `Student.java` (private fields, public getters/setters) |
| **OOP - Inheritance** | `Student` and `Instructor` extend `Person`                        |
| **OOP - Abstraction** | `Person` is an `abstract` class with an `abstract` method `getProfile()` |
| **OOP - Polymorphism** | `student.getProfile()` in `getStudentTranscript()` invokes the correct method |
| **Constructors & `super`** | `Student` constructor calls `super()` to initialize `Person` fields |
| **Immutability** | `CourseCode.java` (final fields, no setters)                        |
| **Nested & Inner Classes** | `Course.Builder` (static nested), `EnrollmentService.EnrollmentFinder` (inner) |
| **Interfaces** | *Planned for future extension (e.g., `Persistable` interface)* |
| **Functional Interfaces & Lambdas**| `CourseService.searchCourses()` uses `Predicate`, lambdas for sorting/filtering |
| **Anonymous Inner Class** | *(Replaced with lambda for comparator for modern syntax)* |
| **Enums with constructors** | `Grade.java`, `Semester.java`                                       |
| **Upcast/Downcast & `instanceof`** | *(Not explicitly needed in this design but can be shown if adding different Person types)* |
| **Overriding & Overloading** | `toString()` and `getProfile()` are overridden in subclasses        |
| **Design Pattern - Singleton** | `AppConfig.getInstance()`                                           |
| **Design Pattern - Builder** | `Course.Builder` nested class                                       |
| **Exceptions (Custom, Checked)** | `MaxCreditLimitExceededException` (checked), `CourseNotFoundException` (unchecked) |
| **`try-catch-finally`** | `CliManager.manageEnrollments()`, `ImportExportService.importData()` |
| **File I/O (NIO.2 - `Path`, `Files`)** | `ImportExportService.java`, `BackupService.java`                    |
| **Streams for File I/O** | `ImportExportService` uses `Files.lines()` and Stream pipelines   |
| **Date/Time API (`java.time`)** | `Student.registrationDate`, `BackupService` timestamp, `Person.dateOfBirth` |
| **Stream API (filter, map, etc.)** | `CourseService.searchCourses()`, `ReportService.generateGpaDistributionReport()` |

## Notes

### Enabling Assertions
Assertions are used in this project (e.g., in `CourseCode.java`) to check for internal invariants. By default, they are disabled at runtime. To enable them:
* **From the Command Line:** Use the `-ea` (enable assertions) flag.
    ```bash
    java -ea -cp bin edu.ccrm.cli.CliManager
    ```
* **In Eclipse:**
    1.  Go to `Run > Run Configurations...`.
    2.  Select your `CliManager` configuration.
    3.  Go to the **Arguments** tab.
    4.  In the **VM arguments** box, type `-ea`.
    5.  Click Apply, then Run.

### Sample Commands
Here is a sample workflow you can follow when running the application:
1.  **List all courses:** `2` (Manage Courses) -> `1` (List All)
2.  **List all students:** `1` (Manage Students) -> `2` (List All)
3.  **Enroll student `S001` in course `CS201`:** `3` (Manage Enrollments) -> `1` (Enroll) -> `S001` -> `CS201`
4.  **Record a grade:** `3` (Manage Enrollments) -> `2` (Record Grade) -> `S001` -> `CS201` -> `85` (marks)
5.  **View student transcript:** `1` (Manage Students) -> `3` (View Transcript) -> `S001`
6.  **Export current data:** `4` (Data Management) -> `1` (Export)
7.  **Create a backup:** `4` (Data Management) -> `2` (Backup)
8.  **View a report:** `5` (View Reports) -> `1` (GPA Distribution)
9.  **Exit:** `6`          

# Application Usage Guide 
This guide provides the necessary data files and a sample command workflow to demonstrate the core features of the Student Course & Enrollment Management System. 
## Sample Data Files 📁 
For the application to work, create a `data` folder in the root of your project and add the following files. 
### `data/students.csv` 
```csv id,regNo,fullName,email,dob 1,S001,Alice Johnson,alice.j@example.com,2002-05-15 2,S002,Bob Williams,bob.w@example.com,2001-08-22 3,S003,Charlie Brown,charlie.b@example.com,2002-11-30
