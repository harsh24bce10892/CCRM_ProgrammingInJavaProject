package edu.ccrm.exceptions;

//custom exception for max credit limit reached
public class MaxCreditLimitExceededException extends Exception{
    public MaxCreditLimitExceededException(String message){
        super(message);
    }
}