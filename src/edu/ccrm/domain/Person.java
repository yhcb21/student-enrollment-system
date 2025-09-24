package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Abstract base class for Student and Instructor.
 * Demonstrates: Abstraction, Inheritance.
 */
public abstract class Person {
    protected int id;
    protected String fullName;
    protected String email;
    protected LocalDate dateOfBirth;

    public Person(int id, String fullName, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getProfile();

    // Getters and Setters (Encapsulation)
    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + fullName + ", Email: " + email;
    }
}