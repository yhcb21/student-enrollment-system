package edu.ccrm.cli;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Predicate;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.DataStore;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.ReportService;
import edu.ccrm.service.StudentService;
import edu.ccrm.util.InputValidator;

/**
 * Main class for the Command-Line Interface.
 */
public class CliManager {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DataStore dataStore = new DataStore();
    private static final StudentService studentService = new StudentService(dataStore);
    private static final CourseService courseService = new CourseService(dataStore);
    private static final EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);
    private static final ReportService reportService = new ReportService(dataStore);
    private static final ImportExportService importExportService = new ImportExportService(dataStore);
    private static final BackupService backupService = new BackupService();

    public static void main(String[] args) {
        System.out.println("Welcome to the Student Course & Enrollment Management System!");
        System.out.println("Configuration loaded. Data directory: " + AppConfig.getInstance().getDataDirectory());
        
        // Load initial data
        importExportService.importData();

        // Demonstrating a labeled jump with break
        OUTER_LOOP:
        while (true) {
            printMainMenu();
            int choice = InputValidator.getInt(scanner, "Enter your choice: ");
            switch (choice) {
                case 1 -> manageStudents();
                case 2 -> manageCourses();
                case 3 -> manageEnrollments();
                case 4 -> manageData();
                case 5 -> showReports();
                case 6 -> {
                    System.out.println("Exiting application...");
                    printPlatformNote();
                    break OUTER_LOOP; // Labeled break to exit the outer loop
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments & Grades");
        System.out.println("4. Import/Export & Backup");
        System.out.println("5. View Reports");
        System.out.println("6. Exit");
    }

    private static void manageStudents() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add New Student");
        System.out.println("2. List All Students");
        System.out.println("3. View Student Profile & Transcript");
        System.out.println("4. Update Student Status");
        int choice = InputValidator.getInt(scanner, "Enter choice: ");
        
        switch (choice) {
            case 1 -> {
                System.out.println("Enter student details:");
                String regNo = InputValidator.getString(scanner, "Registration No: ");
                String name = InputValidator.getString(scanner, "Full Name: ");
                String email = InputValidator.getString(scanner, "Email: ");
                LocalDate dob = LocalDate.parse(InputValidator.getString(scanner, "Date of Birth (YYYY-MM-DD): "));
                Student newStudent = new Student(dataStore.getStudents().size() + 1, regNo, name, email, dob);
                studentService.addStudent(newStudent);
                System.out.println("Student added successfully.");
            }
            case 2 -> {
                System.out.println("\n--- List of All Students ---");
                studentService.getAllStudents().forEach(System.out::println);
            }
            case 3 -> {
                String regNo = InputValidator.getString(scanner, "Enter Student Registration No: ");
                try {
                    System.out.println(studentService.getStudentTranscript(regNo));
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
            case 4 -> {
                 String regNo = InputValidator.getString(scanner, "Enter Student Registration No: ");
                 System.out.println("Available statuses: " + Arrays.toString(Student.StudentStatus.values()));
                 String statusStr = InputValidator.getString(scanner, "Enter new status: ").toUpperCase();
                 try {
                     Student.StudentStatus status = Student.StudentStatus.valueOf(statusStr);
                     studentService.updateStudentStatus(regNo, status);
                     System.out.println("Status updated.");
                 } catch (IllegalArgumentException e) {
                     System.err.println("Invalid status provided.");
                 }
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void manageCourses() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. List All Courses");
        System.out.println("2. Search & Filter Courses");
        int choice = InputValidator.getInt(scanner, "Enter choice: ");

        switch (choice) {
            case 1 -> {
                System.out.println("\n--- List of All Courses ---");
                courseService.getAllCourses().forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("Filter by: 1. Instructor, 2. Department, 3. Semester");
                int filterChoice = InputValidator.getInt(scanner, "Enter filter type: ");
                Predicate<Course> filter = course -> true; // Default: no filter
                
                if (filterChoice == 1) {
                    String name = InputValidator.getString(scanner, "Enter Instructor Name: ");
                    filter = CourseService.filterByInstructor(name);
                } else if (filterChoice == 2) {
                    String dept = InputValidator.getString(scanner, "Enter Department: ");
                    filter = CourseService.filterByDepartment(dept);
                } else if (filterChoice == 3) {
                    String semStr = InputValidator.getString(scanner, "Enter Semester (SPRING, FALL, etc.): ");
                    try {
                        Semester sem = Semester.valueOf(semStr.toUpperCase());
                        filter = CourseService.filterBySemester(sem);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid semester.");
                        return;
                    }
                }
                System.out.println("\n--- Filtered Courses ---");
                courseService.searchCourses(filter).forEach(System.out::println);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void manageEnrollments() {
        System.out.println("\n--- Enrollment & Grading ---");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Record Grade for Student");
        int choice = InputValidator.getInt(scanner, "Enter choice: ");

        String regNo = InputValidator.getString(scanner, "Enter Student Registration No: ");
        String courseCode = InputValidator.getString(scanner, "Enter Course Code (e.g., CS101): ");

        switch (choice) {
            case 1 -> {
                try {
                    enrollmentService.enrollStudent(regNo, courseCode);
                } catch (MaxCreditLimitExceededException | DuplicateEnrollmentException e) {
                    System.err.println("Enrollment Failed: " + e.getMessage());
                } catch (RuntimeException e) { // Catches Student/Course not found
                    System.err.println("Error: " + e.getMessage());
                }
            }
            case 2 -> {
                int marks = InputValidator.getInt(scanner, "Enter marks (0-100): ");
                // Demonstrates try-with-resources (though scanner is static here, it's for syntax demo)
                // and multi-catch block.
                try (Scanner localScanner = new Scanner(System.in)) { // Just for demonstration
                     enrollmentService.recordGrade(regNo, courseCode, marks);
                } catch (NumberFormatException | NullPointerException e) {
                    System.err.println("An unexpected error occurred while processing grades: " + e.getMessage());
                } finally {
                    System.out.println("Grade recording process finished.");
                }
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void manageData() {
        System.out.println("\n--- Data Management ---");
        System.out.println("1. Export All Data");
        System.out.println("2. Create Backup of Exported Data");
        int choice = InputValidator.getInt(scanner, "Enter choice: ");

        switch (choice) {
            case 1 -> importExportService.exportData();
            case 2 -> backupService.createBackup();
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private static void showReports() {
        System.out.println("\n--- Reports ---");
        System.out.println("1. GPA Distribution Report");
        int choice = InputValidator.getInt(scanner, "Enter choice: ");

        if (choice == 1) {
            reportService.generateGpaDistributionReport();
        } else {
            System.out.println("Invalid choice.");
        }
    }
    
    private static void printPlatformNote() {
        System.out.println("\n--- Java Platform Summary ---");
        System.out.println(" * Java SE (Standard Edition): Core Java platform for desktop and server applications.");
        System.out.println(" * Java EE (Enterprise Edition): Built on SE, adds APIs for large-scale, distributed enterprise applications (e.g., web services, servlets).");
        System.out.println(" * Java ME (Micro Edition): A subset of SE for resource-constrained devices like mobile phones and embedded systems.");
        System.out.println("-----------------------------\n");
    }
}