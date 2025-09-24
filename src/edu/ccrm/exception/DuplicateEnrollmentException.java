package edu.ccrm.exception;

// Checked Exception
public class DuplicateEnrollmentException extends Exception {
    public DuplicateEnrollmentException(String message) {
        super(message);
    }
}