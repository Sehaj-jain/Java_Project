package edu.ccrm.domain;

import java.util.Objects;

/**
 * Course class demonstrating Builder Pattern and complex relationships.
 */
public class Course {
    // Required fields - marked final for immutability after construction
    private final String courseCode;
    private final String title;
    private final int credits;

    // Optional fields
    private String description;
    private Instructor instructor;
    private Semester semester;
    private Department department;
    private int maxCapacity;
    private int currentEnrollment;
    private boolean active;

    // Private constructor - forces use of Builder
    private Course(Builder builder) {
        this.courseCode = builder.courseCode;
        this.title = builder.title;
        this.credits = builder.credits;
        this.description = builder.description;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
        this.maxCapacity = builder.maxCapacity;
        this.currentEnrollment = builder.currentEnrollment;
        this.active = builder.active;
    }

    // Getters (no setters to maintain immutability after creation)
    public String getCourseCode() {
        return courseCode;
    }

    public String getCode() {
        return courseCode;
    }
    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public String getDescription() {
        return description;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public Semester getSemester() {
        return semester;
    }

    public Department getDepartment() {
        return department;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    public boolean isActive() {
        return active;
    }

    // Business methods
    public boolean hasSeatsAvailable() {
        return currentEnrollment < maxCapacity;
    }

    public void enrollStudent() {
        if (!hasSeatsAvailable()) {
            throw new IllegalStateException("Course is at full capacity");
        }
        currentEnrollment++;
    }

    public void unenrollStudent() {
        if (currentEnrollment > 0) {
            currentEnrollment--;
        }
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // equals and hashCode based on courseCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    @Override
    public String toString() {
        return String.format("Course{code='%s', title='%s', credits=%d, instructor=%s, semester=%s}",
                courseCode, title, credits,
                instructor != null ? instructor.getFullName() : "None",
                semester != null ? semester : "Not assigned");
    }

    // ========== BUILDER PATTERN IMPLEMENTATION ==========

    /**
     * Static nested Builder class - demonstrates nested classes
     */
    public static class Builder {
        // Required parameters
        private final String courseCode;
        private final String title;
        private final int credits;

        // Optional parameters - initialized to default values
        private String description = "";
        private Instructor instructor = null;
        private Semester semester = null;
        private Department department = null;
        private int maxCapacity = 30;
        private int currentEnrollment = 0;
        private boolean active = true;

        // Builder constructor with required fields
        public Builder(String courseCode, String title, int credits) {
            this.courseCode = Objects.requireNonNull(courseCode, "Course code cannot be null");
            this.title = Objects.requireNonNull(title, "Title cannot be null");

            if (credits < 1 || credits > 6) {
                throw new IllegalArgumentException("Credits must be between 1 and 6");
            }
            this.credits = credits;
        }

        // Methods for optional parameters (return Builder for method chaining)
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder instructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }

        public Builder department(Department department) {
            this.department = department;
            return this;
        }

        public Builder maxCapacity(int maxCapacity) {
            if (maxCapacity < 1) {
                throw new IllegalArgumentException("Max capacity must be positive");
            }
            this.maxCapacity = maxCapacity;
            return this;
        }

        public Builder currentEnrollment(int currentEnrollment) {
            if (currentEnrollment < 0) {
                throw new IllegalArgumentException("Current enrollment cannot be negative");
            }
            this.currentEnrollment = currentEnrollment;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        // Build method that creates the Course object
        public Course build() {
            // Validate business rules before building
            if (currentEnrollment > maxCapacity) {
                throw new IllegalStateException("Current enrollment cannot exceed max capacity");
            }

            return new Course(this);
        }
    }
}