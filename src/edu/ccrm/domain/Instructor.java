package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Instructor class demonstrating Inheritance from Person.
 */
public class Instructor extends Person {
    private String department;
    private String officeLocation;
    private String phoneExtension;
    private List<Course> assignedCourses;

    public Instructor(String id, String employeeId, String fullName, String email, String department) {
        super(id, employeeId, fullName, email);
        this.department = department;
        this.assignedCourses = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "INSTRUCTOR";
    }

    // Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getPhoneExtension() {
        return phoneExtension;
    }

    public void setPhoneExtension(String phoneExtension) {
        this.phoneExtension = phoneExtension;
    }

    public List<Course> getAssignedCourses() {
        return new ArrayList<>(assignedCourses);
    }

    // Business methods
    public void assignCourse(Course course) {
        if (!assignedCourses.contains(course)) {
            assignedCourses.add(course);
        }
    }

    public void removeCourse(Course course) {
        assignedCourses.remove(course);
    }

    @Override
    public String toString() {
        return String.format("Instructor{id='%s', name='%s', department='%s', courses=%d}",
                getId(), getFullName(), department, assignedCourses.size());
    }
}