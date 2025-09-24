package edu.ccrm.service;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

public class EnrollmentService {
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(StudentService ss, CourseService cs) {
        this.studentService = ss;
        this.courseService = cs;
    }

    /**
     * Enrolls a student in a course with business rule checks.
     * Demonstrates: throws clause for checked exceptions.
     */
    public void enrollStudent(String regNo, String courseCode)
            throws MaxCreditLimitExceededException, DuplicateEnrollmentException {
        Student student = studentService.findStudentByRegNo(regNo);
        Course course = courseService.findCourseByCode(courseCode);

        // Rule 1: Check for duplicate enrollment
        boolean alreadyEnrolled = student.getEnrolledCourses().stream()
                .anyMatch(e -> e.getCourse().getCourseCode().getFullCode().equals(courseCode));
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException("Student " + regNo + " is already enrolled in course " + courseCode);
        }

        // Rule 2: Check for max credit limit
        int currentCredits = student.getEnrolledCourses().stream()
                .filter(e -> e.getCourse().getSemester() == course.getSemester())
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();
        
        int maxCredits = AppConfig.getInstance().getMaxCreditsPerSemester();
        if (currentCredits + course.getCredits() > maxCredits) {
            throw new MaxCreditLimitExceededException("Cannot enroll. Exceeds max credit limit of " + maxCredits + " for the semester.");
        }

        Enrollment newEnrollment = new Enrollment(student, course);
        student.addEnrollment(newEnrollment);
        System.out.println("Enrollment successful!");
    }

    public void recordGrade(String regNo, String courseCode, int marks) {
        Student student = studentService.findStudentByRegNo(regNo);
        
        // Using an inner class to find the enrollment
        class EnrollmentFinder {
            Enrollment find() {
                for (Enrollment en : student.getEnrolledCourses()) {
                    if (en.getCourse().getCourseCode().getFullCode().equals(courseCode)) {
                        return en;
                    }
                }
                return null;
            }
        }
        
        EnrollmentFinder finder = new EnrollmentFinder();
        Enrollment enrollment = finder.find();

        if (enrollment != null) {
            enrollment.setGrade(Grade.fromMarks(marks));
            System.out.println("Grade recorded successfully for " + regNo + " in " + courseCode);
        } else {
            System.out.println("Error: Student " + regNo + " is not enrolled in course " + courseCode);
        }
    }
}