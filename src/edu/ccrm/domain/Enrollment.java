package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a student's enrollment in a course with grade recording.
 * Demonstrates business rules and enum usage.
 */
public class Enrollment {
    private final String enrollmentId;
    private final Student student;
    private final Course course;
    private final LocalDate enrollmentDate;
    private final Semester semester;
    private Grade grade;
    private boolean completed;
    private boolean active;

    // Counter for generating unique enrollment IDs
    private static int idCounter = 1;

    public Enrollment(Student student, Course course, Semester semester) {
        this.enrollmentId = "ENR" + String.format("%04d", idCounter++);
        this.student = Objects.requireNonNull(student, "Student cannot be null");
        this.course = Objects.requireNonNull(course, "Course cannot be null");
        this.semester = Objects.requireNonNull(semester, "Semester cannot be null");
        this.enrollmentDate = LocalDate.now();
        this.grade = null;
        this.completed = false;
        this.active = true;

        // Business rule: Check if student can enroll (credit limit)
        if (!student.canEnrollInCourse(course)) {
            throw new IllegalArgumentException(
                    "Student cannot enroll: Credit limit exceeded. Current: " +
                            student.getCurrentSemesterCredits() + ", Course: " + course.getCredits() +
                            ", Max: " + student.getMaxCreditsPerSemester()
            );
        }

        // Business rule: Check if course has available seats
        if (!course.hasSeatsAvailable()) {
            throw new IllegalArgumentException("Course is at full capacity");
        }

        // Enroll the student
        student.enrollInCourse(course);
        course.enrollStudent();
    }

    // Getters
    public String getEnrollmentId() { return enrollmentId; }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public Semester getSemester() { return semester; }
    public Grade getGrade() { return grade; }
    public boolean isCompleted() { return completed; }
    public boolean isActive() { return active; }

    // Business methods
    public void recordGrade(Grade grade) {
        this.grade = Objects.requireNonNull(grade, "Grade cannot be null");
        this.completed = true;
    }

    public void withdraw() {
        this.active = false;
        this.completed = false;
        student.unenrollFromCourse(course);
        course.unenrollStudent();
    }

    public double getGradePoints() {
        return grade != null ? grade.getGradePoints() : 0.0;
    }

    public double getQualityPoints() {
        return getGradePoints() * course.getCredits();
    }

    public boolean hasGrade() {
        return grade != null;
    }

    public String getStatus() {
        if (!active) return "Withdrawn";
        if (completed) return "Completed";
        return "Active";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(enrollmentId, that.enrollmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId);
    }

    @Override
    public String toString() {
        return String.format("Enrollment[%s] %s in %s - Grade: %s, Status: %s",
                enrollmentId, student.getFullName(), course.getCode(),
                grade != null ? grade : "Not Graded", getStatus());
    }
}