package edu.ccrm.exceptions;

//custom exception for duplicate enrollment
public class DuplicateEnrollmentException extends Exception{
    public DuplicateEnrollmentException(String message){
        super(message);
    }
}