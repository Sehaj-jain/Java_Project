package edu.ccrm;

import edu.ccrm.domain.*;

public class TestDomainClasses {
    public static void main(String[] args) {
        // Test Enums
        System.out.println("=== Testing Enums ===");
        System.out.println("Spring semester: " + Semester.SPRING);
        System.out.println("Grade A points: " + Grade.A.getGradePoints());
        System.out.println("Is B passing? " + Grade.B.isPassing());

        // Test Inheritance and Polymorphism
        System.out.println("\n=== Testing Inheritance ===");

        Student student = new Student("S001", "2023001", "John Doe", "john.doe@university.edu");
        student.setDepartment("Computer Science");

        Instructor instructor = new Instructor("I001", "EMP001", "Dr. Smith", "smith@university.edu", "Computer Science");

        // Polymorphism - treating different types as Person
        Person[] people = {student, instructor};

        for (Person person : people) {
            System.out.println(person.getFullName() + " - Role: " + person.getRole());
            System.out.println("String representation: " + person.toString());
        }

        // Test Student methods
        System.out.println("\n=== Testing Student Methods ===");
        System.out.println("Current credits: " + student.getCurrentSemesterCredits());
        System.out.println("Max credits: " + student.getMaxCreditsPerSemester());
    }
}