package edu.ccrm.domain;

import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student extends Person {
    private String department;
    private LocalDate enrollmentDate;
    private int maxCreditsPerSemester;
    private List<Course> enrolledCourses;
    private String regNo;  // Specific registration number

    // SINGLE CONSTRUCTOR - fix the duplicate issue
    public Student(String id, String regNo, String fullName, String email) {
        super(id, regNo, fullName, email); // Pass regNo as registrationNumber to parent
        this.regNo = regNo;
        this.enrollmentDate = LocalDate.now();
        this.maxCreditsPerSemester = 18;
        this.enrolledCourses = new ArrayList<>();
    }

    // Override abstract method from Person
    @Override
    public String getRole() {
        return "STUDENT";
    }


    // GETTER FOR REGNO - only define this once
    public String getRegNo() {
        return regNo;
    }



    // Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public int getMaxCreditsPerSemester() {
        return maxCreditsPerSemester;
    }

    public void setMaxCreditsPerSemester(int maxCreditsPerSemester) {
        if (maxCreditsPerSemester < 1 || maxCreditsPerSemester > 24) {
            throw new IllegalArgumentException("Max credits must be between 1 and 24");
        }
        this.maxCreditsPerSemester = maxCreditsPerSemester;
    }

    public List<Course> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses);
    }

    // Business methods
    public void enrollInCourse(Course course) {
        Objects.requireNonNull(course, "Course cannot be null");
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void unenrollFromCourse(Course course) {
        enrolledCourses.remove(course);
    }

    public int getCurrentSemesterCredits() {
        return enrolledCourses.stream()
                .mapToInt(Course::getCredits)
                .sum();
    }

    public boolean canEnrollInCourse(Course course) {
        return getCurrentSemesterCredits() + course.getCredits() <= maxCreditsPerSemester;
    }

    public List<Enrollment> getCompletedEnrollments(List<Enrollment> allEnrollments) {
        return allEnrollments.stream()
                .filter(enrollment -> enrollment.getStudent().equals(this))
                .filter(Enrollment::isCompleted)
                .filter(Enrollment::hasGrade)
                .collect(Collectors.toList());
    }

    public String getTranscriptHeader() {
        return String.format(
                "TRANSCRIPT FOR: %s (%s)\n" +
                        "ID: %s | Department: %s\n" +
                        "Enrollment Date: %s\n" +
                        "=".repeat(50),
                getFullName(), getRegNo(), getId(),
                department != null ? department : "Undeclared",
                enrollmentDate
        );
    }

    public double calculateGPA() {
        return 0.0; // Placeholder - actual implementation is in EnrollmentService
    }


    @Override
    public String toString() {
        return String.format("Student{id='%s', regNo='%s', name='%s', department='%s'}",
                getId(), regNo, getFullName(), department);
    }


}