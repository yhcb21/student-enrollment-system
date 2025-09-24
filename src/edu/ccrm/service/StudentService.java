package edu.ccrm.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.StudentNotFoundException;

public class StudentService {
    private final DataStore dataStore;

    public StudentService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void addStudent(Student student) {
        dataStore.getStudents().put(student.getRegNo(), student);
    }

    public Student findStudentByRegNo(String regNo) {
        Student student = dataStore.getStudents().get(regNo);
        if (student == null) {
            throw new StudentNotFoundException("Student with Registration No '" + regNo + "' not found.");
        }
        return student;
    }

    public List<Student> getAllStudents() {
        return dataStore.getStudents().values().stream()
                .sorted(Comparator.comparing(Student::getRegNo))
                .collect(Collectors.toList());
    }

    public void updateStudentStatus(String regNo, Student.StudentStatus status) {
        Student student = findStudentByRegNo(regNo);
        student.setStatus(status);
    }

    public String getStudentTranscript(String regNo) {
        Student student = findStudentByRegNo(regNo);
        StringBuilder transcript = new StringBuilder();
        
        // Polymorphism: Calling getProfile() on a Student object
        transcript.append(student.getProfile()).append("\n\n");
        transcript.append("--- Academic Transcript ---\n");

        if (student.getEnrolledCourses().isEmpty()) {
            transcript.append("No courses enrolled yet.\n");
        } else {
            transcript.append(String.format("%-10s | %-30s | %-10s | %s\n", "Code", "Course Title", "Credits", "Grade"));
            transcript.append("------------------------------------------------------------------\n");
            for (Enrollment enrollment : student.getEnrolledCourses()) {
                Course course = enrollment.getCourse();
                transcript.append(String.format("%-10s | %-30s | %-10d | %s\n",
                        course.getCourseCode(), course.getTitle(), course.getCredits(), enrollment.getGrade()));
            }
        }
        transcript.append("------------------------------------------------------------------\n");
        transcript.append(String.format("GPA: %.2f\n", calculateGpa(student)));
        
        return transcript.toString();
    }

    private double calculateGpa(Student student) {
        double totalPoints = 0;
        int totalCredits = 0;

        for (Enrollment en : student.getEnrolledCourses()) {
            if (en.getGrade().getGradePoint() >= 0) { // Exclude 'NA' grades
                totalPoints += en.getGrade().getGradePoint() * en.getCourse().getCredits();
                totalCredits += en.getCourse().getCredits();
            }
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
}