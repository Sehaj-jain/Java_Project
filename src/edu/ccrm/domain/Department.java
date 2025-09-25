package edu.ccrm.domain;

public enum Department {
    COMPUTER_SCIENCE("CS", "Computer Science"),
    ELECTRICAL_ENGINEERING("EE", "Electrical Engineering"),
    MECHANICAL_ENGINEERING("ME", "Mechanical Engineering"),
    BUSINESS_ADMINISTRATION("BA", "Business Administration"),
    MATHEMATICS("MATH", "Mathematics"),
    PHYSICS("PHY", "Physics");

    private final String code;
    private final String fullName;

    private Department(String code, String fullName) {
        this.code = code;
        this.fullName = fullName;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return code + " - " + fullName;
    }
}