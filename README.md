# Campus Course & Records Manager (CCRM)

## Project Overview

Made by: Harsh Ratnaparkhe 24BCE10892

The Campus Course & Records Manager (CCRM) is a full-fledged, console-based Java application that provides simulation of a real-world student management information system. A cli based Java interface with multiple options helps administrators control student records, course catalogs, academic enrollments, and grading. It provides basic functionality like adding and updating students and courses and enrolling students while ensuring basic exception handling (for example, total credits must not exceed a certain number). It gives transcripts to students on which their GPA is calculated.

Designed using a clean architecture with multiple layers, CCRM separates data models (domain), service logic (service), and user interaction (CLI), thereby promoting maintainability and scalability. It tries to marry curiosity on modern Java SE development with a couple of object-oriented principle concepts, Singleton design pattern, Builder design pattern, Exception handling, and a huge array of APIs (Java Streams for data processing and NIO.2 to optimize file I/O). All the application data is persisted on local CSV files, which enable the conversion of states to be saved or loaded from one session to another.

<img width="829" height="918" alt="image" src="https://github.com/user-attachments/assets/32f4e8c4-d806-43d1-aa79-10875f3ad190" />


---

## How to Run

### Prerequisites
  * Java Development Kit (JDK) version 11 or higher.  
  * Eclipse IDE for Java Developers.

### Running from Eclipse
 1. Import the project using  
     **`File` → `Import...` → `General` → `Projects from Folder or Archive`**.
  2. Navigate to  
     `src/main/java/edu/ccrm/cli/CrmApplication.java`.
  3. Right-click on `CrmApplication.java` and select  
     **`Run As` → `Java Application`**.


## Technical Explanations

### Evolution of Java
* **1995** → Java 1.0 – First release; applets + AWT for GUIs.
* **1997** → Java 1.1 – Inner classes, JDBC for databases, JavaBeans.
* **1998** → Java 2 (SE 1.2) – Swing UI, Collections Framework, browser plug‑in.
* **2002** → Java SE 1.4 – assert keyword, exception chaining, NIO for fast I/O.
* **2004** → Java SE 5.0 – Generics, annotations, autoboxing, enhanced for‑each loop.
* **2006** → Java SE 6 – Performance boost, scripting API, stable enterprise use.
* **2011** → Java SE 7 – Try‑with‑resources, diamond operator, NIO.2 (files).
* **2014** → Java SE 8 – Lambdas, Streams API, new Date/Time library.
* **2017** → Java SE 9 – Module system (Jigsaw), JShell REPL.
* **2018** → Java SE 11 (LTS) – var keyword, improved APIs, long‑term support.
* **2021** → Java SE 17 (LTS) – Records, sealed classes, pattern matching.
* **2023** → Java SE 21 (LTS) – Virtual threads (Project Loom), better performance.
### Java ME vs. SE vs. EE

| Feature    | Java ME (Micro Edition)     | Java SE (Standard Edition) | Java EE (Enterprise Edition)       |
| ---------- | --------------------------- | -------------------------- | ---------------------------------- |
| **Target** | Embedded Devices, IoT       | Desktop & Server Apps      | Large-scale, Web Applications      |
| **Core API** | Subset of Java SE           | Core Java Language         | Superset of Java SE                |
| **Example**| Old Mobile Games            | **This CCRM Project** | Online Banking Systems             |

### Java Architecture: JDK, JRE, JVM
* **JVM (Java Virtual Machine):** An abstract machine that provides the runtime environment to execute Java bytecode. This is what makes Java "write once, run anywhere."
* **JRE (Java Runtime Environment):** The software package needed to *run* a Java application. It includes the JVM and core libraries.
* **JDK (Java Development Kit):** The full kit for *building* Java applications. It includes everything in the JRE plus development tools like the compiler (`javac`).

---

## Setup and Installation

### Windows Java Install Steps
1.  Download a JDK (e.g., version 17 LTS) from a provider like [Adoptium](https://adoptium.net/).
2.  Run the installer and follow the on-screen instructions.
3.  Verify the installation by opening a Command Prompt and running `java -version`.

<img width="1220" height="648" alt="image" src="https://github.com/user-attachments/assets/5bb9a410-b516-49cd-b036-53974d9008fa" />


### Eclipse Setup Steps
1.  Import the project using the `File -> Import...` wizard.
2.  The project structure will appear in the "Package Explorer".
3.  To run, right-click `CrmApplication.java` and select `Run As -> Java Application`.

<img width="636" height="638" alt="image" src="https://github.com/user-attachments/assets/4e6a4a09-8526-4813-9a7a-ae5f1784ecfa" />


---

## Enabling Assertions
To enable assertions for internal checks:
1.  Go to **`Run` -> `Run Configurations...`**.
2.  Select your application configuration.
3.  Go to the **`Arguments`** tab.
4.  In the **`VM arguments`** box, add the flag: **`-ea`**.

<img width="786" height="638" alt="image" src="https://github.com/user-attachments/assets/f0fe73d3-6dc0-40df-85a6-a42af72dcdc7" />


---

## Mapping Table

| Syllabus Topic            | File / Location of Demonstration                           |
| ------------------------- | ---------------------------------------------------------- |
| **OOP (All Pillars)** | `Person` (abstract), `Student`/`Instructor` (inheritance)   |
| **Design Pattern: Singleton**| `edu.ccrm.config.AppConfig.java`                           |
| **Design Pattern: Builder** | `edu.ccrm.domain.Course.java`                              |
| **Custom Interface** | `edu.ccrm.interfaces.Searchable.java`                      |
| **Custom Exceptions** | `edu.ccrm.exceptions` package                              |
| **NIO.2 & File I/O** | `ImportExportService.java`, `BackupService.java`          |
| **Java Stream API** | `TranscriptService.java` (GPA Calc), `CourseService.java` |
| **Recursion** | `edu.ccrm.util.DirectoryUtils.java`                        |
| **Anonymous Inner Class** | `CrmApplication.java` (in `manageStudents` method)      |

---

## Demo video link

https://drive.google.com/file/d/1XNSZRGteqEXzAotTmAwZwpT0rBIP25FJ/view?usp=sharing
