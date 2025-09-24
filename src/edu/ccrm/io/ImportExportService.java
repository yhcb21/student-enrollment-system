package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.CourseCode;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.service.DataStore;

/**
 * Service for importing and exporting data using NIO.2 and Streams.
 */
public class ImportExportService {
    private final DataStore dataStore;
    private final Path dataDir = Paths.get(AppConfig.getInstance().getDataDirectory());

    public ImportExportService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void importData() {
        try {
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
            importInstructors();
            importCourses();
            importStudents();
            System.out.println("Data imported successfully from '" + dataDir + "' directory.");
        } catch (IOException e) {
            System.err.println("Error during data import: " + e.getMessage());
        }
    }
    
    // In a real app, instructors would also be in a CSV
    private void importInstructors() {
        dataStore.getInstructors().put(1, new Instructor(1, "Dr. Alan Turing", "alan.t@bletchley.uk", LocalDate.of(1912, 6, 23), "Computer Science", "A101"));
        dataStore.getInstructors().put(2, new Instructor(2, "Dr. Grace Hopper", "grace.h@yale.edu", LocalDate.of(1906, 12, 9), "Computer Science", "B202"));
    }

    private void importCourses() throws IOException {
        Path coursesFile = dataDir.resolve("courses.csv");
        if (!Files.exists(coursesFile)) return;
        
        try (Stream<String> lines = Files.lines(coursesFile)) {
            lines.skip(1) // Skip header
                 .map(line -> line.split(","))
                 .forEach(parts -> {
                     CourseCode code = new CourseCode(parts[0].replaceAll("\\d", ""), Integer.parseInt(parts[0].replaceAll("\\D", "")));
                     Instructor instructor = dataStore.getInstructors().get(Integer.parseInt(parts[4]));
                     Course course = new Course.Builder(code, parts[1])
                                             .credits(Integer.parseInt(parts[2]))
                                             .semester(Semester.valueOf(parts[3].toUpperCase()))
                                             .instructor(instructor)
                                             .department(parts[5])
                                             .build();
                     dataStore.getCourses().put(code.getFullCode(), course);
                 });
        }
    }

    private void importStudents() throws IOException {
        Path studentsFile = dataDir.resolve("students.csv");
        if (!Files.exists(studentsFile)) return;
        
        try (Stream<String> lines = Files.lines(studentsFile)) {
            lines.skip(1) // Skip header
                 .map(line -> line.split(","))
                 .forEach(parts -> {
                    Student student = new Student(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], LocalDate.parse(parts[4]));
                    dataStore.getStudents().put(student.getRegNo(), student);
                 });
        }
    }

    public void exportData() {
        try {
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
            }
            exportStudents();
            exportCourses();
            exportEnrollments();
            System.out.println("Data exported successfully to '" + dataDir + "' directory.");
        } catch (IOException e) {
            System.err.println("Error during data export: " + e.getMessage());
        }
    }
    
    private void exportStudents() throws IOException {
        Path studentsFile = dataDir.resolve("students_export.csv");
        List<String> lines = dataStore.getStudents().values().stream()
                .map(s -> String.join(",", String.valueOf(s.getId()), s.getRegNo(), s.getFullName(), s.getEmail(), s.getDateOfBirth().toString(), s.getStatus().name()))
                .collect(Collectors.toList());
        lines.add(0, "id,regNo,fullName,email,dob,status");
        Files.write(studentsFile, lines);
    }

    private void exportCourses() throws IOException {
         Path coursesFile = dataDir.resolve("courses_export.csv");
        List<String> lines = dataStore.getCourses().values().stream()
                .map(c -> String.join(",", c.getCourseCode().getFullCode(), c.getTitle(), String.valueOf(c.getCredits()), c.getSemester().name(), String.valueOf(c.getInstructor().getId()), c.getDepartment()))
                .collect(Collectors.toList());
        lines.add(0, "courseCode,title,credits,semester,instructorId,department");
        Files.write(coursesFile, lines);
    }

    private void exportEnrollments() throws IOException {
        Path enrollmentsFile = dataDir.resolve("enrollments_export.csv");
        List<String> lines = dataStore.getStudents().values().stream()
                .flatMap(s -> s.getEnrolledCourses().stream())
                .map(en -> String.join(",", en.getStudent().getRegNo(), en.getCourse().getCourseCode().getFullCode(), en.getGrade().name()))
                .collect(Collectors.toList());
        lines.add(0, "regNo,courseCode,grade");
        Files.write(enrollmentsFile, lines);
    }
}