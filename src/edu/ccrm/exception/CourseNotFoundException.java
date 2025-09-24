package edu.ccrm.exception;

// Unchecked Exception
public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}