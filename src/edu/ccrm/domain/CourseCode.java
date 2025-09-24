package edu.ccrm.domain;

/**
 * An immutable value class for Course Code.
 * Demonstrates: Immutability (final fields, no setters).
 */
public final class CourseCode {
    private final String departmentPrefix;
    private final int courseNumber;

    public CourseCode(String departmentPrefix, int courseNumber) {
        // Assertion to check for invariants (internal consistency)
        assert departmentPrefix != null && !departmentPrefix.trim().isEmpty() : "Department prefix cannot be null or empty";
        assert courseNumber > 0 : "Course number must be positive";
        this.departmentPrefix = departmentPrefix;
        this.courseNumber = courseNumber;
    }

    public String getFullCode() {
        return departmentPrefix + courseNumber;
    }

    @Override
    public String toString() {
        return getFullCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CourseCode that = (CourseCode) obj;
        return courseNumber == that.courseNumber && departmentPrefix.equals(that.departmentPrefix);
    }

    @Override
    public int hashCode() {
        return 31 * departmentPrefix.hashCode() + courseNumber;
    }
}