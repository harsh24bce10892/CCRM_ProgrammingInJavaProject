package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import edu.ccrm.exceptions.DuplicateEnrollmentException;
import edu.ccrm.exceptions.MaxCreditLimitExceededException;

import java.util.Optional;

//this handles the enrollment of students into a course
public class EnrollmentService{

    //max credits
    private static final int MAX_CREDITS_PER_SEMESTER = 18;

    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService){
        this.studentService = studentService;
        this.courseService = courseService;
    }

    //enrolls students if all conditions are satisfied
    public void enrollStudent(String regNo, String courseCode)
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException, Exception{
        
        //find the student and course. Use Optional to handle 'not found' cases
        Student student = studentService.findById(regNo)
                .orElseThrow(() -> new Exception("Student with registration number " + regNo + " not found."));
        
        Course course = courseService.findById(courseCode)
                .orElseThrow(() -> new Exception("Course with code " + courseCode + " not found."));

        //check for duplicate enrollment
        boolean alreadyEnrolled = student.getEnrollments().stream()
                .anyMatch(enrollment -> enrollment.getCourse().equals(course));
        if(alreadyEnrolled){
            throw new DuplicateEnrollmentException("Student is already enrolled in course " + courseCode);
        }

        //check the credit limit rule
        int currentCredits = student.getEnrollments().stream()
                .mapToInt(enrollment -> enrollment.getCourse().getCredits())
                .sum();
        
        if((currentCredits + course.getCredits()) > MAX_CREDITS_PER_SEMESTER){
            throw new MaxCreditLimitExceededException("Cannot enroll. Exceeds max credit limit of " + MAX_CREDITS_PER_SEMESTER);
        }

        //if all checks pass, create the enrollment and add it to the student
        Enrollment newEnrollment = new Enrollment(student, course);
        student.addEnrollment(newEnrollment);
        
        System.out.println("Enrollment successful for " + student.getFullName() + " in " + course.getTitle());
    }

    //assign grade to a student
    public boolean assignGrade(String regNo, String courseCode, Grade grade){
        Optional<Student> studentOpt = studentService.findById(regNo);
        if(studentOpt.isPresent()){
            Student student = studentOpt.get();
            
            //find the specific enrollment for this course
            Optional<Enrollment> enrollmentOpt = student.getEnrollments().stream()
                    .filter(e -> e.getCourse().getCode().equals(courseCode))
                    .findFirst();
            
            if(enrollmentOpt.isPresent()){
                enrollmentOpt.get().assignGrade(grade);
                System.out.println("Grade " + grade + " assigned to " + student.getFullName() + " for " + courseCode);
                return true;
            }
        }
        System.out.println("Error: Could not find enrollment for student " + regNo + " in course " + courseCode);
        return false;
    }
    
    public boolean unenrollStudent(String regNo, String courseCode){
        Optional<Student> studentOpt = studentService.findById(regNo);
        if(studentOpt.isPresent()){
            Student student = studentOpt.get();
            
            Optional<Enrollment> enrollmentOpt = student.getEnrollments().stream()
                    .filter(e -> e.getCourse().getCode().equals(courseCode))
                    .findFirst();
            
            if(enrollmentOpt.isPresent()){
                student.removeEnrollment(enrollmentOpt.get()); // Assuming you have a removeEnrollment method in Student
                System.out.println("Successfully unenrolled " + student.getFullName() + " from " + courseCode);
                return true;
            }
        }
        System.out.println("Error: Could not find enrollment to remove.");
        return false;
    }
}