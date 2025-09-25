package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.io.FileDataService;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentService studentService = new StudentService();
    private static CourseService courseService = new CourseService();
    private static EnrollmentService enrollmentService = new EnrollmentService();
    private static FileDataService fileDataService = new FileDataService(studentService, courseService);

    public static void main(String[] args) {
        System.out.println("=== Campus Course & Records Manager ===");
        AppConfig config = AppConfig.getInstance();
        config.displayConfig();
        fileDataService.initializeDataFiles();

        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Enrollment Management");
            System.out.println("4. File Operations");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    showStudentMenu();
                    break;
                case 2:
                    showCourseMenu();
                    break;
                case 3:
                    showEnrollmentMenu();
                    break;
                case 4:
                    showFileOperationsMenu();
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    private static void showStudentMenu() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add Student");
        System.out.println("2. List All Students");
        System.out.println("3. Find Student by ID");
        System.out.println("4. Back to Main Menu");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                listStudents();
                break;
            case 3:
                findStudent();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid option!");
        }
    }

    private static void addStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Registration Number: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        Student student = new Student(id, regNo, name, email);
        studentService.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private static void listStudents() {
        System.out.println("\n--- All Students ---");
        studentService.getAllStudents().forEach(System.out::println);
    }

    private static void findStudent() {
        System.out.print("Enter Student ID to find: ");
        String id = scanner.nextLine();
        Student student = studentService.findStudentById(id);
        if (student != null) {
            System.out.println("Found: " + student);
        } else {
            System.out.println("Student not found!");
        }
    }

    private static void showCourseMenu() {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. Add Course");
        System.out.println("2. List All Courses");
        System.out.println("3. Back to Main Menu");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addCourse();
                break;
            case 2:
                listCourses();
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid option!");
        }
    }

    private static void addCourse() {
        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        System.out.print("Enter Course Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();

        Course course = new Course.Builder(code, title, credits).build();
        courseService.addCourse(course);
        System.out.println("Course added successfully!");
    }

    private static void listCourses() {
        System.out.println("\n--- All Courses ---");
        courseService.getAllCourses().forEach(System.out::println);
    }

    private static void showEnrollmentMenu() {
        while (true) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Record Grade for Enrollment");
            System.out.println("3. Withdraw Student from Course");
            System.out.println("4. List All Enrollments");
            System.out.println("5. Generate Student Transcript");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    enrollStudentInCourse();
                    break;
                case 2:
                    recordGrade();
                    break;
                case 3:
                    withdrawStudent();
                    break;
                case 4:
                    listAllEnrollments();
                    break;
                case 5:
                    generateTranscript();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private static void enrollStudentInCourse() {
        try {
            System.out.print("Enter Student ID: ");
            String studentId = scanner.nextLine();
            System.out.print("Enter Course Code: ");
            String courseCode = scanner.nextLine();
            System.out.print("Enter Semester (SPRING/SUMMER/FALL): ");
            String semesterStr = scanner.nextLine().toUpperCase();

            Student student = studentService.findStudentById(studentId);  // FIXED METHOD NAME
            Course course = courseService.findCourseByCode(courseCode);
            Semester semester = Semester.valueOf(semesterStr);  // NOW Semester WILL BE RECOGNIZED

            if (student == null || course == null) {
                System.out.println("Student or Course not found!");
                return;
            }

            Enrollment enrollment = enrollmentService.enrollStudent(student, course, semester);
            System.out.println("Enrollment successful! Enrollment ID: " + enrollment.getEnrollmentId());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void recordGrade() {
        try {
            System.out.print("Enter Enrollment ID: ");
            String enrollmentId = scanner.nextLine();
            System.out.print("Enter Grade (A/B/C/D/F): ");
            String gradeStr = scanner.nextLine().toUpperCase();

            Grade grade = Grade.valueOf(gradeStr);
            enrollmentService.recordGrade(enrollmentId, grade);
            System.out.println("Grade recorded successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void generateTranscript() {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();

        Student student = studentService.findStudentById(studentId);
        if (student != null) {
            String transcript = enrollmentService.generateTranscript(student);
            System.out.println("\n" + transcript);
        } else {
            System.out.println("Student not found!");
        }
    }

    private static void listAllEnrollments() {
        System.out.println("\n--- All Enrollments ---");
        enrollmentService.getAllEnrollments().forEach(System.out::println);
    }

    private static void withdrawStudent() {
        try {
            System.out.print("Enter Enrollment ID to withdraw: ");
            String enrollmentId = scanner.nextLine();

            enrollmentService.withdrawStudent(enrollmentId);
            System.out.println("Student withdrawn successfully!");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Add this new menu method:
    private static void showFileOperationsMenu() {
        while (true) {
            System.out.println("\n=== FILE OPERATIONS ===");
            System.out.println("1. Import Students from CSV");
            System.out.println("2. Import Courses from CSV");
            System.out.println("3. Export Students to CSV");
            System.out.println("4. Export Courses to CSV");
            System.out.println("5. Create Backup");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    fileDataService.importStudentsFromCSV();
                    break;
                case 2:
                    fileDataService.importCoursesFromCSV();
                    break;
                case 3:
                    fileDataService.exportStudentsToCSV();
                    break;
                case 4:
                    fileDataService.exportCoursesToCSV();
                    break;
                case 5:
                    fileDataService.createBackup();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}