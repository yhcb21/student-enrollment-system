package edu.ccrm.domain;

import java.time.LocalDateTime;

/**
 * Represents the enrollment of a student in a course.
 */
public class Enrollment {
    private final Student student;
    private final Course course;
    private Grade grade;
    private final LocalDateTime enrollmentDate;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDateTime.now();
        this.grade = Grade.NA; // Default grade
    }
    
    // Getters and Setters
    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }
}