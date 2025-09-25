package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentService {
    private final List<Enrollment> enrollments = new ArrayList<>();

    public Enrollment enrollStudent(Student student, Course course, Semester semester) {
        // Check if student is already enrolled
        boolean alreadyEnrolled = enrollments.stream()
                .anyMatch(e -> e.getStudent().equals(student) &&
                        e.getCourse().equals(course) &&
                        e.isActive());

        if (alreadyEnrolled) {
            throw new IllegalArgumentException("Student already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment(student, course, semester);
        enrollments.add(enrollment);
        return enrollment;
    }

    public void recordGrade(String enrollmentId, Grade grade) {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        if (enrollment != null) {
            enrollment.recordGrade(grade);
        } else {
            throw new IllegalArgumentException("Enrollment not found: " + enrollmentId);
        }
    }

    public void withdrawStudent(String enrollmentId) {
        Enrollment enrollment = findEnrollmentById(enrollmentId);
        if (enrollment != null) {
            enrollment.withdraw();
        } else {
            throw new IllegalArgumentException("Enrollment not found: " + enrollmentId);
        }
    }

    public Enrollment findEnrollmentById(String enrollmentId) {
        return enrollments.stream()
                .filter(e -> e.getEnrollmentId().equals(enrollmentId))
                .findFirst()
                .orElse(null);
    }

    public List<Enrollment> getEnrollmentsByStudent(Student student) {
        return enrollments.stream()
                .filter(e -> e.getStudent().equals(student))
                .collect(Collectors.toList());
    }

    public List<Enrollment> getEnrollmentsByCourse(Course course) {
        return enrollments.stream()
                .filter(e -> e.getCourse().equals(course))
                .collect(Collectors.toList());
    }

    public List<Enrollment> getActiveEnrollments() {
        return enrollments.stream()
                .filter(Enrollment::isActive)
                .collect(Collectors.toList());
    }

    /**
     * Calculate GPA for a student - THIS METHOD WAS MISSING
     */
    public double calculateGPA(Student student) {
        List<Enrollment> studentEnrollments = getEnrollmentsByStudent(student);

        // Filter only completed enrollments with grades
        List<Enrollment> completedEnrollments = studentEnrollments.stream()
                .filter(Enrollment::isCompleted)
                .filter(Enrollment::hasGrade)
                .collect(Collectors.toList());

        if (completedEnrollments.isEmpty()) {
            return 0.0;
        }

        // Calculate total quality points (grade points × credits)
        double totalQualityPoints = completedEnrollments.stream()
                .mapToDouble(enrollment -> {
                    double gradePoints = enrollment.getGrade().getGradePoints();
                    int credits = enrollment.getCourse().getCredits();
                    return gradePoints * credits;
                })
                .sum();

        // Calculate total credits
        int totalCredits = completedEnrollments.stream()
                .mapToInt(enrollment -> enrollment.getCourse().getCredits())
                .sum();

        // GPA = total quality points ÷ total credits
        return totalCredits > 0 ? totalQualityPoints / totalCredits : 0.0;
    }

    /**
     * Generate transcript for a student
     */
    public String generateTranscript(Student student) {
        StringBuilder transcript = new StringBuilder();

        // Use the student's method
        transcript.append(student.getTranscriptHeader()).append("\n\n");

        transcript.append("COURSE WORK:\n");
        transcript.append("-".repeat(50)).append("\n");
        transcript.append(String.format("%-10s %-30s %-5s %-10s %-8s\n",
                "Code", "Title", "Credits", "Grade", "Points"));
        transcript.append("-".repeat(50)).append("\n");

        // Add each enrollment to the transcript
        getEnrollmentsByStudent(student).forEach(enrollment -> {
            String gradeDisplay = enrollment.getGrade() != null ?
                    enrollment.getGrade().name() : "In Progress";
            String pointsDisplay = enrollment.getGrade() != null ?
                    String.format("%.1f", enrollment.getGrade().getGradePoints()) : "-";

            transcript.append(String.format("%-10s %-30s %-5s %-10s %-8s\n",
                    enrollment.getCourse().getCode(),
                    enrollment.getCourse().getTitle(),
                    enrollment.getCourse().getCredits() + "cr",
                    gradeDisplay,
                    pointsDisplay
            ));
        });

        // Calculate and display GPA
        double gpa = calculateGPA(student);
        transcript.append("\n").append("-".repeat(50)).append("\n");
        transcript.append(String.format("CUMULATIVE GPA: %.2f", gpa));

        return transcript.toString();
    }

    public void addEnrollment(Enrollment enrollment) {
        if (enrollment != null) {
            enrollments.add(enrollment);
        }
    }

    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }
}