package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;
import edu.ccrm.exceptions.DuplicateCourseException;
import edu.ccrm.interfaces.Searchable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseService implements Searchable<Course, String>{

    private final Map<String, Course> coursesByCode = new HashMap<>();

    public Course createCourse(String code, String title, int credits, String department, Semester semester)
            throws DuplicateCourseException{
        if(coursesByCode.containsKey(code)){
            throw new DuplicateCourseException("Error: Course with code " + code + " already exists.");
        }
        Course newCourse = new Course.Builder(code, title)
                .credits(credits)
                .department(department)
                .semester(semester)
                .build();
        coursesByCode.put(code, newCourse);
        return newCourse;
    }

    @Override
    public Optional<Course> findById(String code){
        assert code != null : "Course code cannot be null";
        return Optional.ofNullable(coursesByCode.get(code));
    }

    @Override
    public List<Course> findAll(){
        return new ArrayList<>(coursesByCode.values());
    }

    public boolean assignInstructorToCourse(String code, Instructor instructor){
        Optional<Course> courseOptional = findById(code); // Use the new method name
        courseOptional.ifPresent(course -> course.assignInstructor(instructor));
        return courseOptional.isPresent();
    }

    public List<Course> findCoursesByDepartment(String department){
        return coursesByCode.values().stream()
                .filter(course -> course.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    public List<Course> findCoursesByInstructor(Instructor instructor){
        return coursesByCode.values().stream()
                .filter(course -> instructor.equals(course.getInstructor()))
                .collect(Collectors.toList());
    }
}