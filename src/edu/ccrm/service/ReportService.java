package edu.ccrm.service;

import java.util.Map;
import java.util.stream.Collectors;

import edu.ccrm.domain.Grade;

public class ReportService {
    private final DataStore dataStore;

    public ReportService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * Generates a report on GPA distribution across all students.
     * Demonstrates: Stream pipeline with aggregation (Collectors.groupingBy).
     */
    public void generateGpaDistributionReport() {
        System.out.println("\n--- GPA Distribution Report ---");

        Map<String, Long> gpaDistribution = dataStore.getStudents().values().stream()
                .flatMap(student -> student.getEnrolledCourses().stream())
                .map(enrollment -> enrollment.getGrade())
                .filter(grade -> grade != Grade.NA)
                .collect(Collectors.groupingBy(
                        grade -> getGpaRange(grade.getGradePoint()),
                        Collectors.counting()
                ));

        gpaDistribution.forEach((range, count) ->
                System.out.printf("GPA Range %s: %d students\n", range, count));
        System.out.println("-----------------------------\n");
    }

    private String getGpaRange(double gradePoint) {
        if (gradePoint >= 9.0) return "9.0 - 10.0 (S/A)";
        if (gradePoint >= 8.0) return "8.0 - 8.9 (B)";
        if (gradePoint >= 7.0) return "7.0 - 7.9 (C)";
        if (gradePoint >= 6.0) return "6.0 - 6.9 (D)";
        if (gradePoint >= 5.0) return "5.0 - 5.9 (E)";
        return "< 5.0 (F)";
    }
}