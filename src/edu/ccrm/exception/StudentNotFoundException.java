package edu.ccrm.exception;

// Unchecked Exception
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}