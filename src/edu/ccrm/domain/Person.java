package edu.ccrm.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Abstract base class for all persons in the system.
 * Demonstrates Abstraction and will be used for Inheritance.
 */
public abstract class Person {
    // Private fields - Encapsulation
    private String id;
    private String registrationNumber;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private boolean active;

    // Constructor
    protected Person(String id, String registrationNumber, String fullName, String email) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.registrationNumber = registrationNumber;
        this.fullName = Objects.requireNonNull(fullName, "Full name cannot be null");
        this.email = validateEmail(email);
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    // Abstract method - forces subclasses to implement
    public abstract String getRole();

    // Email validation
    private String validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
        return email;
    }

    // Getters and Setters (Encapsulation)
    public String getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = Objects.requireNonNull(fullName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = validateEmail(email);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Deactivate method with business logic
    public void deactivate() {
        this.active = false;
    }

    // Activate method
    public void activate() {
        this.active = true;
    }

    // equals and hashCode based on ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString method - will be overridden in subclasses (Polymorphism)
    @Override
    public String toString() {
        return String.format("Person{id='%s', name='%s', email='%s', active=%s}",
                id, fullName, email, active);
    }
}