package edu.ccrm.domain;

public enum Semester {
    // Enum constants with constructor parameters
    SPRING("Spring Semester"),
    SUMMER("Summer Semester"),
    FALL("Fall Semester"),
    WINTER("Winter Session");

    // Private field - demonstrates encapsulation in enums
    private final String displayName;

    // Enum constructor (always private)
    private Semester(String displayName) {
        this.displayName = displayName;
    }

    // Getter method
    public String getDisplayName() {
        return displayName;
    }

    // Utility method to find enum by name (case-insensitive)
    public static Semester fromString(String text) {
        for (Semester semester : Semester.values()) {
            if (semester.displayName.equalsIgnoreCase(text) ||
                    semester.name().equalsIgnoreCase(text)) {
                return semester;
            }
        }
        throw new IllegalArgumentException("No semester with text: " + text);
    }

    @Override
    public String toString() {
        return displayName;
    }
}