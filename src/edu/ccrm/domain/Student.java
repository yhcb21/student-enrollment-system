package edu.ccrm.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student, extending the Person class.
 * Demonstrates: Inheritance, Encapsulation.
 */
public class Student extends Person {
    private String regNo;
    private StudentStatus status;
    private List<Enrollment> enrolledCourses;
    private final LocalDateTime registrationDate;

    public enum StudentStatus {
        ACTIVE, INACTIVE, GRADUATED
    }

    public Student(int id, String regNo, String fullName, String email, LocalDate dateOfBirth) {
        super(id, fullName, email, dateOfBirth);
        this.regNo = regNo;
        this.status = StudentStatus.ACTIVE;
        this.enrolledCourses = new ArrayList<>();
        this.registrationDate = LocalDateTime.now();
    }

    @Override
    public String getProfile() {
        return String.format("--- Student Profile ---\n" +
                        "Registration No: %s\n" +
                        "Full Name: %s\n" +
                        "Email: %s\n" +
                        "Status: %s\n" +
                        "Registration Date: %s\n" +
                        "-----------------------",
                regNo, fullName, email, status, registrationDate);
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrolledCourses.add(enrollment);
    }

    // Getters and Setters
    public String getRegNo() {
        return regNo;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public List<Enrollment> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses); // Defensive copy
    }
}