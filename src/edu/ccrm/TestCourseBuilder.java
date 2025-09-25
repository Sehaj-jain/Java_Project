package edu.ccrm;

import edu.ccrm.domain.*;
import edu.ccrm.service.EnrollmentService;

public class TestCourseBuilder {
    public static void main(String[] args) {
        // Create instructor
        Instructor drSmith = new Instructor("I001", "EMP001", "Dr. Smith", "smith@uni.edu", "Computer Science");

        // Create course
        Course javaCourse = new Course.Builder("CS101", "Java Programming", 3)
                .instructor(drSmith)
                .semester(Semester.FALL)
                .department(Department.COMPUTER_SCIENCE)
                .build();

        // Create student
        Student student = new Student("S001", "2023001", "Alice Johnson", "alice@student.edu");

        // Test enrollment
        Enrollment enrollment = new Enrollment(student, javaCourse, Semester.FALL);
        enrollment.recordGrade(Grade.A);

        // Test GPA calculation
        EnrollmentService enrollmentService = new EnrollmentService();
        enrollmentService.addEnrollment(enrollment);

        double gpa = enrollmentService.calculateGPA(student);
        System.out.println("Student GPA: " + gpa);

        // Test transcript
        String transcript = enrollmentService.generateTranscript(student);
        System.out.println("\nTranscript:\n" + transcript);
    }
}