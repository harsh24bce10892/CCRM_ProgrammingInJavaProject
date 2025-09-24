package edu.ccrm.cli;

import java.nio.file.Files;
import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.*;
import edu.ccrm.util.DirectoryUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CrmApplication{

    private static final Scanner scanner = new Scanner(System.in);
    private static final Path DATA_DIR = Paths.get("test-data");
    private static final Path BACKUP_DIR = Paths.get("backups");
    
    private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;
    private static TranscriptService transcriptService;
    private static ImportExportService importExportService;
    private static BackupService backupService;
    //Dummy instructor
    private static Instructor dummyInstructor; 

    public static void main(String[] args){
        AppConfig.getInstance();
        studentService = new StudentService();
        courseService = new CourseService();
        enrollmentService = new EnrollmentService(studentService, courseService);
        transcriptService = new TranscriptService();
        backupService = new BackupService();
        importExportService = new ImportExportService(courseService, studentService, enrollmentService);
        dummyInstructor = new Instructor("Dr. Alan Turing", "alan@bletchley.uk", "I001", "Computer Science");
        System.out.println("Platform Note: This is a Java SE (Standard Edition) application.");
        seedInitialData();
        runMainMenu();

        scanner.close();
        System.out.println("Thank you for using CCRM. Goodbye!");
    }

    private static void runMainMenu(){
        boolean running = true;
        while (running){
            displayMainMenu();
            try{
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice){
                    case 1 -> manageStudents();
                    case 2 -> searchPeople(); 
                    case 3 -> manageEnrollments();
                    case 4 -> printStudentTranscript();
                    case 5 -> importExportService.importData();
                    case 6 -> importExportService.exportData();
                    case 7 -> backupService.performBackup(DATA_DIR, BACKUP_DIR);
                    case 8 -> showBackupSize();
                    case 9 -> running = false;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    //menu display
    private static void displayMainMenu(){
        System.out.println("\n--- CCRM Main Menu ---");
        System.out.println("1. Manage Students (Add/List with Sort)");
        System.out.println("2. List All People (Student/Instructor)");
        System.out.println("3. Manage Enrollments & Grades");
        System.out.println("4. Print Student Transcript");
        System.out.println("--- File Utilities ---");
        System.out.println("5. Import Data from Files");
        System.out.println("6. Export Data to Files");
        System.out.println("7. Create Backup");
        System.out.println("8. Show Total Backup Size");
        System.out.println("9. Exit");
    }

    //manage students
    private static void manageStudents(){
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add New Student");
        System.out.println("2. List All Students (Sorted by Name)");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1){
            System.out.print("Enter Full Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Registration Number: ");
            String regNo = scanner.nextLine();
            studentService.createStudent(name, email, regNo);
        } else if (choice == 2){
            List<Student> students = studentService.findAll();
            if (students.isEmpty()){
                System.out.println("No students found.");
            } else{
                // ANONYMOUS INNER CLASS DEMONSTRATION
                students.sort(new Comparator<Student>() {
                    @Override
                    public int compare(Student s1, Student s2) {
                        return s1.getFullName().compareTo(s2.getFullName());
                    }
                });
                System.out.println("--- All Students (Sorted by Name) ---");
                students.forEach(System.out::println);
            }
        }
    }

    //search method
    private static void searchPeople(){
        //This method demonstrates the 'instanceof' keyword.
        List<Person> people = new ArrayList<>();
        people.addAll(studentService.findAll());
        people.add(dummyInstructor); //add our dummy instructor to the list

        System.out.println("\n--- All People in System ---");
        for (Person p : people) {
            System.out.print(p.getFullName() + " is a ");
            
            //INSTANCEOF CHECK
            if (p instanceof Student) {
                Student s = (Student) p; //Downcasting
                System.out.println("Student with RegNo: " + s.getRegistrationNumber());
            } else if (p instanceof Instructor) {
                Instructor i = (Instructor) p; //Downcasting
                System.out.println("Instructor in Dept: " + i.getDepartment());
            }
        }
    }
    
    //manage the enrollments
    private static void manageEnrollments(){
        System.out.println("\n--- Enrollment Management ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Assign Grade");
        System.out.println("3. Unenroll Student from Course");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        try{
            if (choice == 1){
                System.out.print("Enter Student Registration No: ");
                String regNo = scanner.nextLine();
                System.out.print("Enter Course Code: ");
                String courseCode = scanner.nextLine();
                enrollmentService.enrollStudent(regNo, courseCode);

            } else if (choice == 2){
                System.out.print("Enter Student Registration No: ");
                String regNo = scanner.nextLine();
                System.out.print("Enter Course Code: ");
                String courseCode = scanner.nextLine();
                System.out.print("Enter Grade (S, A, B, C, D, E, F): ");
                String gradeStr = scanner.nextLine().toUpperCase();
                Grade grade = Grade.valueOf(gradeStr); //this can throw an error if input is invalid
                enrollmentService.assignGrade(regNo, courseCode, grade);

            } else if (choice == 3){
                System.out.print("Enter Student Registration No: ");
                String regNo = scanner.nextLine();
                System.out.print("Enter Course Code: ");
                String courseCode = scanner.nextLine();
                enrollmentService.unenrollStudent(regNo, courseCode);
                
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (Exception e){
            System.err.println("Operation Failed: " + e.getMessage());
        }
    }
    
    //print transcript
    private static void printStudentTranscript(){
        System.out.print("Enter Student Registration No to print transcript: ");
        String regNo = scanner.nextLine();
        
        Optional<Student> studentOpt = studentService.findById(regNo);
        
        if(studentOpt.isPresent()){
            String transcript = transcriptService.generateTranscript(studentOpt.get());
            System.out.println(transcript);
        } else {
            System.out.println("Student not found.");
        }
    }

    //backup info
    private static void showBackupSize(){
        System.out.println("Calculating size of backup directory: " + BACKUP_DIR);
        try{
            if(!Files.exists(BACKUP_DIR)){
                System.out.println("Backup directory does not exist yet.");
                return;
            }
            long sizeInBytes = DirectoryUtils.getDirectorySize(BACKUP_DIR);
            double sizeInKB = sizeInBytes / 1024.0;
            System.out.printf("Total size of all backups: %.2f KB\n", sizeInKB);
        } catch (IOException e){
            System.err.println("Error calculating directory size: " + e.getMessage());
        }
    }
    
    //initialize the starting data
    private static void seedInitialData(){
        System.out.println("Attempting to load initial data from /data folder...");
        
        // This is the line that reads data from your CSV files.
        importExportService.importData();

        // After importing, we can still perform actions on the loaded data.
        // For example, let's assign a grade to one of the students we just imported.
        try{
            enrollmentService.assignGrade("S001", "CS101", Grade.A);
        } catch (Exception e){
            System.err.println("Could not assign grade to seeded student: " + e.getMessage());
        }
    }
}