package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        if (student != null) {
            students.add(student);
        }
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    public Student findByReg(String regNo) {
        for (Student student : students) {
            if (student.getRegNo().equals(regNo)) {
                return student;
            }
        }
        return null;
    }
}