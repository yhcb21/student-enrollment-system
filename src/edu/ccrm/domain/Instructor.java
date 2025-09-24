package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Represents an instructor, extending the Person class.
 */
public class Instructor extends Person {
    private String department;
    private String officeLocation;

    public Instructor(int id, String fullName, String email, LocalDate dateOfBirth, String department, String officeLocation) {
        super(id, fullName, email, dateOfBirth);
        this.department = department;
        this.officeLocation = officeLocation;
    }

    @Override
    public String getProfile() {
        return String.format("--- Instructor Profile ---\n" +
                        "Name: %s\n" +
                        "Department: %s\n" +
                        "Office: %s\n" +
                        "--------------------------",
                fullName, department, officeLocation);
    }

    // Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }
}