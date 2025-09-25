package edu.ccrm.service;

import edu.ccrm.domain.Course;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public Course findCourseByCode(String code) {
        return courses.stream()
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}