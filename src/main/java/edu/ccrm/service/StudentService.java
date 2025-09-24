package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.StudentStatus;
import edu.ccrm.interfaces.Searchable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//logic for the student object is within this
// The class now implements our custom Searchable interface.
public class StudentService implements Searchable<Student, String>{

    //hashmap to store as it allows o(1) finding
    private final Map<String, Student> studentsByRegNo = new HashMap<>();

    //create student and add them in the hashmap
    public Student createStudent(String fullName, String email, String registrationNumber){
        //check if already exists to prevent duplicates
        if(studentsByRegNo.containsKey(registrationNumber)){
            //exception here
            System.out.println("Error: Student with registration number " + registrationNumber + " already exists.");
            return null;
        }

        Student newStudent = new Student(fullName, email, registrationNumber);
        studentsByRegNo.put(registrationNumber, newStudent);
        System.out.println("Successfully created student: " + newStudent.getFullName());
        return newStudent;
    }

    //find student by their registration ID
    @Override
    public Optional<Student> findById(String registrationNumber){
        // An assertion to check for invalid input before processing.
        assert registrationNumber != null : "Registration number cannot be null";
        return Optional.ofNullable(studentsByRegNo.get(registrationNumber));
    }

    //list all students
    @Override
    public List<Student> findAll(){
        return new ArrayList<>(studentsByRegNo.values());
    }

    //updates status of existing students
    public boolean updateStudentStatus(String registrationNumber, StudentStatus newStatus){
        Optional<Student> studentOptional = findById(registrationNumber);
        
        //if optional has a value (student is present) then execute this
        studentOptional.ifPresent(student -> {
            student.setStatus(newStatus);
            System.out.println("Updated status for " + student.getFullName() + " to " + newStatus);
        });

        return studentOptional.isPresent();
    }
}