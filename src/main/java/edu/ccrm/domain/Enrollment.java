package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Enrollment{

    private final Student student;
    private final Course course;
    private final LocalDate enrollmentDate;
    private Grade grade;//grade can be changed so not final

    //constructor
    public Enrollment(Student student, Course course){
        Objects.requireNonNull(student, "Student cannot be null");
        Objects.requireNonNull(course, "Course cannot be null");
        
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDate.now();
        this.grade = Grade.NOT_GRADED; //default NG 
    }

    //assign grade 
    public void assignGrade(Grade grade){
        this.grade = grade;
    }

    //getter functions
    public Student getStudent(){
        return student;
    }

    public Course getCourse(){
        return course;
    }

    public LocalDate getEnrollmentDate(){
        return enrollmentDate;
    }

    public Grade getGrade(){
        return grade;
    }
    
    @Override
    public String toString(){
        return String.format(
            "Enrollment[Student: %s, Course: %s, Grade: %s]",
            student.getFullName(), course.getCode(), grade
        );
    }
    
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        //an enrollment is unique based on the combination of student and course
        return student.equals(that.student) && course.equals(that.course);
    }

    @Override
    public int hashCode(){
        return Objects.hash(student, course);
    }
}