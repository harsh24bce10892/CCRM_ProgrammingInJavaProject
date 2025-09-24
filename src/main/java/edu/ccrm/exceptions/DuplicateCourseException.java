package edu.ccrm.exceptions;

//custom exception
public class DuplicateCourseException extends Exception{
    public DuplicateCourseException(String message){
        super(message);
    }
}