package edu.ccrm.service;

import java.util.HashMap;
import java.util.Map;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Student;

/**
 * A simple in-memory data store for the application.
 */
public class DataStore {
    private final Map<String, Student> students = new HashMap<>(); // Key: regNo
    private final Map<String, Course> courses = new HashMap<>(); // Key: courseCode
    private final Map<Integer, Instructor> instructors = new HashMap<>(); // Key: instructorId
    
    // Getters for maps
    public Map<String, Student> getStudents() {
        return students;
    }

    public Map<String, Course> getCourses() {
        return courses;
    }
    
    public Map<Integer, Instructor> getInstructors() {
        return instructors;
    }
}
