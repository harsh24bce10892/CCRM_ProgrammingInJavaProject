package edu.ccrm.domain;

import java.util.Objects;

/*
  Course class object is an immutable object, which means once a Course object is created,
  it cannot be changed. This prevents accidental changes.
  To achieve this, we use the Builder Pattern and all fields are final.
 */
public final class Course{ //final class means it cannot be subclassed

    private final String code;
    private final String title; 
    private final int credits;
    private final String department;
    private final Semester semester;

    //a course can be created without an instructor, so its not final
    private Instructor instructor;

    /**
    The constructor is private
    This is a key part of the Builder pattern. It forces us to use the Builder to create a Course object, giving us full control over the creation process
    It takes a Builder object and copies the values
    */
    private Course(Builder builder){
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.department = builder.department;
        this.semester = builder.semester;
        this.instructor = builder.instructor;
    }

    //getter functions only as the class is immutable, setters wont work
    public String getCode(){
        return code;
    }

    public String getTitle(){
        return title;
    }

    public int getCredits(){
        return credits;
    }
    
    public String getDepartment(){
        return department;
    }

    public Semester getSemester(){
        return semester;
    }

    public Instructor getInstructor(){
        return instructor;
    }

    //the only setter function is for assigning an instructor, as this can happen after creation
    public void assignInstructor(Instructor instructor){
        this.instructor = instructor;
    }

    @Override
    public String toString(){
        String instructorName = (instructor != null) ? instructor.getFullName() : "Not Assigned";
        return String.format("Course[Code=%s, Title='%s', Credits=%d, Instructor=%s]",
            code, title, credits, instructorName
        );
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return code.equals(course.code); //courses are unique by their course code
    }

    @Override
    public int hashCode(){
        return Objects.hash(code);
    }

    // The builder class implementation
    /*
    This is a static nested class for our Builder
    Its static so we can access it like new Course.Builder()
    It holds a temporary, mutable copy of the course data
    */
    public static class Builder{
        //required parameters
        private final String code;
        private final String title;

        //optional parameters : initialized to default values
        private int credits = 3;
        private String department = "General";
        private Semester semester = Semester.FALL;
        private Instructor instructor = null;

        /*
        The builder's constructor only takes the required fields and everything else is optional and can be set using chained methods
        */
        public Builder(String code, String title){
            this.code = code;
            this.title = title;
        }

        //setter functions
        //each one returns builder objects to allow chaining
        public Builder credits(int credits){
            this.credits = credits;
            return this; //returns this for chaining
        }

        public Builder department(String department){
            this.department = department;
            return this;
        }

        public Builder semester(Semester semester){
            this.semester = semester;
            return this;
        }
        
        public Builder instructor(Instructor instructor){
            this.instructor = instructor;
            return this;
        }

        /*
        This method creates the actual immutable Course object by calling its private constructor and returns an instance of Course
        */
        public Course build(){
            return new Course(this);
        }
    }
}