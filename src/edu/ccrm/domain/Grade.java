package edu.ccrm.domain;

/**
 * Enum for letter grades and their corresponding grade points.
 * Demonstrates: Enum with constructor and fields.
 */
public enum Grade {
    S(10.0), A(9.0), B(8.0), C(7.0), D(6.0), E(5.0), F(0.0), NA(-1.0);

    private final double gradePoint;

    Grade(double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public static Grade fromMarks(int marks) {
        if (marks >= 90) return S;
        if (marks >= 80) return A;
        if (marks >= 70) return B;
        if (marks >= 60) return C;
        if (marks >= 50) return D;
        if (marks >= 40) return E;
        return F;
    }
}
