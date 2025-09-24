package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Instructor is also a child class of Person class
public class Instructor extends Person{

    private String employeeId;
    private String department;
    private LocalDate hireDate;

    //keep track of courses a instructor taught
    private final List<Course> coursesTaught = new ArrayList<>();

    public Instructor(String fullName, String email, String employeeId, String department){
        //super keyword to initialize common fields using parent class constructors
        super(fullName, email);

        //check if null or not
        Objects.requireNonNull(employeeId, "Employee ID cannot be null");
        Objects.requireNonNull(department, "Department cannot be null");

        this.employeeId = employeeId;
        this.department = department;
        this.hireDate = LocalDate.now();
    }

    //override the getProfileDetails for intstructor class as well
    @Override
    public String getProfileDetails(){
        return String.format(
            "--- Instructor Profile ---\n" +
            "ID: %d\n" +
            "Employee ID: %s\n" +
            "Name: %s\n" +
            "Email: %s\n" +
            "Department: %s\n" +
            "Hire Date: %s\n" +
            "Courses Taught: %d",
            id, employeeId, fullName, email, department, hireDate, coursesTaught.size()
        );
    }

    //Methods to manage the assigned courses
    public void assignCourse(Course course){
        if (course != null && !coursesTaught.contains(course)) {
            this.coursesTaught.add(course);
        }
    }

    public void removeCourse(Course course){
        this.coursesTaught.remove(course);
    }

    //getters and setters functions
    public String getEmployeeId(){
        return employeeId;
    }

    public void setEmployeeId(String employeeId){
        this.employeeId = employeeId;
    }

    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public LocalDate getHireDate(){
        return hireDate;
    }

    public List<Course> getCoursesTaught(){
        return new ArrayList<>(coursesTaught);
    }

    //override toString again to generate a formatted output
    @Override
    public String toString(){
        return String.format("Instructor[ID=%d, EmpID=%s, Name=%s, Dept=%s]",
            id, employeeId, fullName, department
        );
    }
}
