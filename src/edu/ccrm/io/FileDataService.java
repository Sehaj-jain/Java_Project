package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.StudentService;
import edu.ccrm.service.CourseService;
import edu.ccrm.config.AppConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Demonstrates NIO.2 File operations and Streams API
 */
public class FileDataService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final AppConfig config;

    public FileDataService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.config = AppConfig.getInstance();
    }

    public void initializeDataFiles() {
        createSampleDataFiles();
    }

    private void createSampleDataFiles() {
        createSampleStudentsFile();
        createSampleCoursesFile();
    }

    private void createSampleStudentsFile() {
        Path filePath = config.getStudentDataFile();
        if (Files.exists(filePath)) {
            System.out.println("students.csv already exists");
            return;
        }

        String sampleData = "id,regNo,fullName,email,department\n" +
                "S001,2023001,John Doe,john.doe@university.edu,Computer Science\n" +
                "S002,2023002,Jane Smith,jane.smith@university.edu,Electrical Engineering\n" +
                "S003,2023003,Mike Johnson,mike.johnson@university.edu,Mathematics\n" +
                "S004,2023004,Sarah Wilson,sarah.wilson@university.edu,Business Administration\n" +
                "S005,2023005,David Brown,david.brown@university.edu,Physics";

        try {
            Files.writeString(filePath, sampleData);
            System.out.println("Created sample students.csv file");
        } catch (IOException e) {
            System.err.println("Error creating sample file: " + e.getMessage());
        }
    }

    private void createSampleCoursesFile() {
        Path filePath = config.getCourseDataFile();
        if (Files.exists(filePath)) {
            System.out.println("courses.csv already exists");
            return;
        }

        String sampleData = "code,title,credits,department,maxCapacity\n" +
                "CS101,Introduction to Java Programming,3,COMPUTER_SCIENCE,25\n" +
                "CS201,Data Structures and Algorithms,4,COMPUTER_SCIENCE,20\n" +
                "MATH101,Calculus I,4,MATHEMATICS,30\n" +
                "EE101,Circuit Analysis,3,ELECTRICAL_ENGINEERING,15\n" +
                "BA201,Business Management,3,BUSINESS_ADMINISTRATION,35\n" +
                "PHY101,General Physics,4,PHYSICS,25";

        try {
            Files.writeString(filePath, sampleData);
            System.out.println("Created sample courses.csv file");
        } catch (IOException e) {
            System.err.println("Error creating sample file: " + e.getMessage());
        }
    }

    // ========== IMPORT METHODS ==========

    public void importStudentsFromCSV() {
        Path filePath = config.getStudentDataFile();
        System.out.println("Importing students from: " + filePath.toAbsolutePath());

        try (Stream<String> lines = Files.lines(filePath)) {
            List<Student> students = lines
                    .skip(1)
                    .map(this::parseStudentFromCSV)
                    .filter(student -> student != null)
                    .collect(Collectors.toList());

            students.forEach(studentService::addStudent);
            System.out.println("Successfully imported " + students.size() + " students");

        } catch (IOException e) {
            System.err.println("Error reading students file: " + e.getMessage());
        }
    }

    public void importCoursesFromCSV() {
        Path filePath = config.getCourseDataFile();
        System.out.println("Importing courses from: " + filePath.toAbsolutePath());

        try (Stream<String> lines = Files.lines(filePath)) {
            List<Course> courses = lines
                    .skip(1)
                    .map(this::parseCourseFromCSV)
                    .filter(course -> course != null)
                    .collect(Collectors.toList());

            courses.forEach(courseService::addCourse);
            System.out.println("Successfully imported " + courses.size() + " courses");

        } catch (IOException e) {
            System.err.println("Error reading courses file: " + e.getMessage());
        }
    }

    // ========== EXPORT METHODS ==========

    public void exportStudentsToCSV() {
        Path filePath = config.getStudentDataFile();
        System.out.println("Exporting students to: " + filePath.toAbsolutePath());

        String header = "id,regNo,fullName,email,department\n";

        String csvData = studentService.getAllStudents().stream()
                .map(this::convertStudentToCSV)
                .collect(Collectors.joining("\n"));

        try {
            Files.write(filePath, (header + csvData).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Exported " + studentService.getAllStudents().size() + " students");
        } catch (IOException e) {
            System.err.println("Error writing students file: " + e.getMessage());
        }
    }

    public void exportCoursesToCSV() {
        Path filePath = config.getCourseDataFile();
        System.out.println("Exporting courses to: " + filePath.toAbsolutePath());

        String header = "code,title,credits,department,maxCapacity,currentEnrollment\n";

        String csvData = courseService.getAllCourses().stream()
                .map(this::convertCourseToCSV)
                .collect(Collectors.joining("\n"));

        try {
            Files.write(filePath, (header + csvData).getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Exported " + courseService.getAllCourses().size() + " courses");
        } catch (IOException e) {
            System.err.println("Error writing courses file: " + e.getMessage());
        }
    }

    // ========== PARSING METHODS ==========

    private Student parseStudentFromCSV(String csvLine) {
        try {
            String[] fields = csvLine.split(",");
            if (fields.length >= 4) {
                Student student = new Student(
                        fields[0].trim(),
                        fields[1].trim(),
                        fields[2].trim(),
                        fields[3].trim()
                );
                if (fields.length >= 5) {
                    student.setDepartment(fields[4].trim());
                }
                return student;
            }
        } catch (Exception e) {
            System.err.println("Error parsing student line: " + csvLine);
        }
        return null;
    }

    private Course parseCourseFromCSV(String csvLine) {
        try {
            String[] fields = csvLine.split(",");
            if (fields.length >= 3) {
                Course.Builder builder = new Course.Builder(
                        fields[0].trim(),
                        fields[1].trim(),
                        Integer.parseInt(fields[2].trim())
                );

                if (fields.length >= 4) {
                    try {
                        Department dept = Department.valueOf(fields[3].trim().toUpperCase());
                        builder.department(dept);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid department: " + fields[3]);
                    }
                }

                if (fields.length >= 5) {
                    builder.maxCapacity(Integer.parseInt(fields[4].trim()));
                }

                return builder.build();
            }
        } catch (Exception e) {
            System.err.println("Error parsing course line: " + csvLine);
        }
        return null;
    }

    private String convertStudentToCSV(Student student) {
        return String.format("%s,%s,%s,%s,%s",
                student.getId(),
                student.getRegNo(),
                student.getFullName(),
                student.getEmail(),
                student.getDepartment() != null ? student.getDepartment() : ""
        );
    }

    private String convertCourseToCSV(Course course) {
        return String.format("%s,%s,%d,%s,%d,%d",
                course.getCode(),
                course.getTitle(),
                course.getCredits(),
                course.getDepartment() != null ? course.getDepartment().name() : "",
                course.getMaxCapacity(),
                course.getCurrentEnrollment()
        );
    }

    // ========== BACKUP METHOD ==========

    public void createBackup() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path backupDir = config.getBackupDirectory().resolve("backup_" + timestamp);

        try {
            Files.createDirectories(backupDir);
            System.out.println("Creating backup in: " + backupDir.toAbsolutePath());

            Files.copy(config.getStudentDataFile(), backupDir.resolve("students.csv"));
            Files.copy(config.getCourseDataFile(), backupDir.resolve("courses.csv"));

            System.out.println("Backup completed successfully!");

            long backupSize = calculateDirectorySize(backupDir);
            System.out.println("Backup size: " + backupSize + " bytes");

        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }

    // ========== RECURSIVE DIRECTORY SIZE CALCULATION ==========

    private long calculateDirectorySize(Path directory) {
        try {
            return Files.walk(directory)
                    .filter(path -> Files.isRegularFile(path))
                    .mapToLong(path -> {
                        try {
                            return Files.size(path);
                        } catch (IOException e) {
                            return 0L;
                        }
                    })
                    .sum();
        } catch (IOException e) {
            System.err.println("Error calculating directory size: " + e.getMessage());
            return 0L;
        }
    }
}