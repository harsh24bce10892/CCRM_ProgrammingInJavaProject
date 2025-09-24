package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ImportExportService{

    private static final Path DATA_DIRECTORY = Paths.get("test-data");
    private static final String COURSES_FILE = "courses.csv";
    private static final String STUDENTS_FILE = "students.csv";
    private static final String ENROLLMENTS_FILE = "enrollments.csv";

    private final CourseService courseService;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    public ImportExportService(CourseService cs, StudentService ss, EnrollmentService es){
        this.courseService = cs;
        this.studentService = ss;
        this.enrollmentService = es;
    }

    public void exportData(){
        System.out.println("Starting data export...");
        try{
            Files.createDirectories(DATA_DIRECTORY);
            Path coursesPath = DATA_DIRECTORY.resolve(COURSES_FILE);
            try (var writer = Files.newBufferedWriter(coursesPath)){
                writer.write("code,title,credits,department,semester\n");
                for (Course course : courseService.findAll()){
                    String line = String.join(",",
                            course.getCode(),
                            course.getTitle(),
                            String.valueOf(course.getCredits()),
                            course.getDepartment(),
                            course.getSemester().name());
                    writer.write(line + "\n");
                }
            }
            System.out.println("Data export completed successfully.");
        } catch (IOException e){
            System.err.println("Error: Failed to export data. " + e.getMessage());
        }
    }

    public void importData(){
        System.out.println("Starting data import from directory: " + DATA_DIRECTORY.toAbsolutePath());
        importCourses();
        importStudents();
        importEnrollments();
        System.out.println("Data import finished.");
    }
    
    private void importCourses(){
        Path path = DATA_DIRECTORY.resolve(COURSES_FILE);
        if (!Files.exists(path)) return;
        
        try (Stream<String> lines = Files.lines(path)){
            lines.skip(1)
                 .map(line -> line.split(","))
                 .forEach(parts -> {
                     try{
                         courseService.createCourse(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], Semester.valueOf(parts[4]));
                     } catch(Exception e){
                         System.err.println("Skipping malformed course line: " + String.join(",", parts));
                     }
                 });
        } catch (IOException e){
            System.err.println("Error reading courses file: " + e.getMessage());
        }
    }

    private void importStudents(){
        Path path = DATA_DIRECTORY.resolve(STUDENTS_FILE);
        if (!Files.exists(path)) return;

        try (Stream<String> lines = Files.lines(path)){
            lines.skip(1)
                 .map(line -> line.split(","))
                 .forEach(parts -> {
                     try{
                         studentService.createStudent(parts[1], parts[2], parts[0]);
                     } catch (Exception e){
                         System.err.println("Skipping malformed student line: " + String.join(",", parts));
                     }
                 });
        } catch (IOException e){
            System.err.println("Error reading students file: " + e.getMessage());
        }
    }
    
    private void importEnrollments(){
        Path path = DATA_DIRECTORY.resolve(ENROLLMENTS_FILE);
        if (!Files.exists(path)) return;
        
        try (Stream<String> lines = Files.lines(path)){
            lines.skip(1)
                 .map(line -> line.split(","))
                 .forEach(parts -> {
                     try{
                         enrollmentService.enrollStudent(parts[0], parts[1]);
                     } catch (Exception e){
                         System.err.println("Skipping malformed enrollment line: " + e.getMessage());
                     }
                 });
        } catch (IOException e){
            System.err.println("Error reading enrollments file: " + e.getMessage());
        }
    }
}