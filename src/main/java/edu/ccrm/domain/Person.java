package edu.ccrm.domain;

import java.util.Objects;

//abstract class so it can only be used by the classes in the same package
public abstract class Person{
    //members are protected to allow inherited class to access them
    protected long id;
    protected String fullName;
    protected String email;

    private static long idCounter = 0; //for generating different id number for different persons. Its private and static so its the same for all other instances for the objects of the Person class and generates unique id for each object


    //constructor
    public Person(String fullName, String email){
        //check if the input is null or not
        Objects.requireNonNull(fullName, "Full name cannot be null");
        Objects.requireNonNull(email, "Email cannot be null");

        this.id = ++idCounter; //Assign unique id to each Person object
        this.fullName = fullName;
        this.email = email;
    }

    //abstract method (every class inheriting this class must have its own implementation of this method)
    public abstract String getProfileDetails();


    //getters and setters functions
    public long getId(){
        return id;
    }
    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    //overriding toString to make it print the details in a structured format
    @Override
    public String toString(){
        return "Person [Id=" + ", Name=" + fullName + ", Email=" + email + "]";
    }


    //overriding equals and hash for not allowing duplicate objects creation
    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Person person  = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}