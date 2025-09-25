package edu.ccrm.domain;

public enum Grade {
    // Enum constants with grade points
    A(4.0, "Excellent"),
    A_MINUS(3.7, "Very Good"),
    B_PLUS(3.3, "Good"),
    B(3.0, "Above Average"),
    B_MINUS(2.7, "Average"),
    C_PLUS(2.3, "Below Average"),
    C(2.0, "Satisfactory"),
    D(1.0, "Poor"),
    F(0.0, "Fail"),
    W(0.0, "Withdrawn"),
    I(0.0, "Incomplete");

    // Private fields
    private final double gradePoints;
    private final String description;

    // Constructor
    private Grade(double gradePoints, String description) {
        this.gradePoints = gradePoints;
        this.description = description;
    }

    // Getters
    public double getGradePoints() {
        return gradePoints;
    }

    public String getDescription() {
        return description;
    }

    // Method to get grade from percentage score
    public static Grade fromPercentage(double percentage) {
        if (percentage >= 93) return A;
        else if (percentage >= 90) return A_MINUS;
        else if (percentage >= 87) return B_PLUS;
        else if (percentage >= 83) return B;
        else if (percentage >= 80) return B_MINUS;
        else if (percentage >= 77) return C_PLUS;
        else if (percentage >= 73) return C;
        else if (percentage >= 60) return D;
        else return F;
    }

    // Check if grade is passing
    public boolean isPassing() {
        return this != F && this != W && this != I;
    }

    @Override
    public String toString() {
        return name() + " (" + gradePoints + " points) - " + description;
    }
}