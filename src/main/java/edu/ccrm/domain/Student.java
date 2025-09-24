package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student extends Person{
    private String regNo;
    private StudentStatus status;
    private LocalDate registrationDate;

    private List<Enrollment> enrollments = new ArrayList<>();
    

    public Student(String fullName, String email, String regNo){
        //super keyword is used here to set the persons name and email using the implementation in the parent class itself. And it should be the first line.
        super(fullName, email);

        //check for null or not
        Objects.requireNonNull(regNo, "Registration number cannot be null");
        this.regNo = regNo;

        //default value for status is set to ACTIVE
        this.status = StudentStatus.ACTIVE; 
        this.registrationDate = LocalDate.now();
    }

    //implementation of the abstract methods in the parent class
    @Override
    public String getProfileDetails(){
        return String.format(
            "--- Student Profile ---\n" +
            "ID: %d\n" +
            "Registration No: %s\n" +
            "Name: %s\n" +
            "Email: %s\n" +
            "Status: %s\n" +
            "Registered On: %s\n" +
            "Enrolled Courses: %d",
            id, regNo, fullName, email, status, registrationDate, enrollments.size()
        );
    }
    
    //Methods to manage enrollments
    public void addEnrollment(Enrollment enrollment){
        this.enrollments.add(enrollment);
    }
    
    public void removeEnrollment(Enrollment enrollment){
        this.enrollments.remove(enrollment);
    }

    //Getters and setters functions
    public String getRegistrationNumber(){
        return regNo;
    }

    public void setRegistrationNumber(String regNo){
        this.regNo = regNo;
    }

    public StudentStatus getStatus(){
        return status;
    }

    public void setStatus(StudentStatus status){
        this.status = status;
    }

    public LocalDate getRegistrationDate(){
        return registrationDate;
    }

    public List<Enrollment> getEnrollments(){
        return new ArrayList<>(enrollments);
    }

    //override toString to provide a formatted information
    @Override
    public String toString(){
        return String.format("Student[ID=%d, RegNo=%s, Name=%s, Status=%s]",
            id, regNo, fullName, status
        );
    }

    
}
