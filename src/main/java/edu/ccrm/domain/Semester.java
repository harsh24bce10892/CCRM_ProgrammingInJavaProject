package edu.ccrm.domain;

//Semester is an enum as it can only have 4 values spring summer fall and winter
public enum Semester{
    SPRING("Spring"),
    SUMMER("Summer"),
    FALL("Fall"),
    WINTER("Winter");

    //final keyword makes it immutable and any methods cant be overriden
    private final String displayName;

    //constructor
    Semester(String displayName){
        this.displayName = displayName;
    }

    //getter function to display name
    public String getDisplayName(){
        return displayName;
    }

    @Override
    public String toString(){
        return this.displayName;
    }
}