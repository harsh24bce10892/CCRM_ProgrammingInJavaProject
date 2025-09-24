# CCRM Usage Guide

This document provides instructions on how to compile, run, and use the
Campus Course & Records Manager (CCRM) application.

------------------------------------------------------------------------

## Getting Started

### 1. Prerequisites

-   A Java Development Kit (JDK), version 11 or higher, must be
    installed.
-   The sample CSV files must be present in a `data` folder in the
    project's root directory.

### 2. Compile 

Navigate to the project's root folder in your terminal or command prompt
and run the following commands to compile the source code.

``` bash
# Create a directory for the compiled files
mkdir bin

# Compile all .java source files into the bin directory
javac -d bin -sourcepath src/main/java src/main/java/edu/ccrm/cli/CrmApplication.java
```

### 3. Run

Once compilation is successful, run the application with the following
command. The -ea flag is included to enable assertions.

``` bash
java -cp bin -ea edu.ccrm.cli.CrmApplication
```

The application will start, load the initial data from the /data folder,
and display the main menu.

### Main Menu Options

The application is controlled by entering the number corresponding to
the desired action.

1.  Manage Students (Add/List with Sort): Opens a sub-menu to either add
    a new student to the system or display a list of all existing
    students, sorted alphabetically by name.

2.  List All People (Student/Instructor): Displays a combined list of
    all students and instructors currently in the system, identifying
    the role of each person.

3.  Print Student Transcript: Prompts for a student's registration
    number and then displays their complete academic transcript,
    including all enrolled courses, grades, and the calculated GPA.

4.  Import Data from Files: Manually triggers the import process,
    loading all data from the CSV files located in the /data directory.

5.  Export Data to Files: Saves the current state of all students,
    courses, and enrollments back to the CSV files in the /data
    directory.

6.  Create Backup: Copies all files from the /data directory into a new,
    unique, timestamped folder inside the /backups directory.

7.  Show Total Backup Size: Recursively calculates and displays the
    total disk space used by all files in the /backups directory.

8.  Exit: Shuts down the application.

### Sample Workflow

Here is a sample session to demonstrate the application's features:

1.  Start the application. Data from the CSV files is automatically
    imported.
2.  Enter 2 to list all people and see the students and instructor
    loaded from the files.
3.  Enter 3 to print a transcript. When prompted, enter S001. You will
    see the transcript for Alice Johnson.
4.  Enter 1 to manage students, then 1 again to add a new student.
    Provide the requested details.
5.  Enter 1 and then 2 to see the updated list of students, now
    including the one you added.
6.  Enter 5 to export the current data, which now includes your new
    student.
7.  Enter 6 to create a backup of the newly exported data.
8.  Enter 9 to exit the application.
