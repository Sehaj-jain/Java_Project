package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Singleton class for application configuration.
 * Demonstrates Singleton design pattern.
 */
public class AppConfig {
    // Static variable reference of single_instance
    private static AppConfig single_instance = null;

    // Configuration fields
    private final Path dataDirectory;
    private final Path backupDirectory;
    private final String appVersion;
    private final int maxLoginAttempts;

    // Private constructor restricts instantiation
    private AppConfig() {
        this.dataDirectory = Paths.get("./data");
        this.backupDirectory = Paths.get("./backups");
        this.appVersion = "1.0.0";
        this.maxLoginAttempts = 3;

        // Create directories if they don't exist
        createDirectories();
    }

    // Static method to create instance of Singleton class
    public static synchronized AppConfig getInstance() {
        if (single_instance == null) {
            single_instance = new AppConfig();
        }
        return single_instance;
    }

    private void createDirectories() {
        try {
            if (!java.nio.file.Files.exists(dataDirectory)) {
                java.nio.file.Files.createDirectories(dataDirectory);
            }
            if (!java.nio.file.Files.exists(backupDirectory)) {
                java.nio.file.Files.createDirectories(backupDirectory);
            }
        } catch (Exception e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }

    // Getters
    public Path getDataDirectory() {
        return dataDirectory;
    }

    public Path getBackupDirectory() {
        return backupDirectory;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public int getMaxLoginAttempts() {
        return maxLoginAttempts;
    }

    // Utility methods
    public Path getStudentDataFile() {
        return dataDirectory.resolve("students.csv");
    }

    public Path getCourseDataFile() {
        return dataDirectory.resolve("courses.csv");
    }

    public Path getEnrollmentDataFile() {
        return dataDirectory.resolve("enrollments.csv");
    }

    public void displayConfig() {
        System.out.println("=== Application Configuration ===");
        System.out.println("Version: " + appVersion);
        System.out.println("Data Directory: " + dataDirectory.toAbsolutePath());
        System.out.println("Backup Directory: " + backupDirectory.toAbsolutePath());
        System.out.println("Max Login Attempts: " + maxLoginAttempts);
    }
}