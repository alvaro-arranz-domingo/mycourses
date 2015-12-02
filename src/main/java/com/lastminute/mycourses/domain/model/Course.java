package com.lastminute.mycourses.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by administrator on 1/12/15.
 */
public class Course {

    private Long id;
    private String name;
    private String description;
    private Teacher teacher;
    private BigDecimal price;
    private Collection<Student> students = new ArrayList<Student>();

    public Course(Long id, String name, String description, Teacher teacher, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public boolean containsStudent(Student student) {
        return students.contains(student);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
                Objects.equals(name, course.name) &&
                Objects.equals(description, course.description) &&
                Objects.equals(teacher, course.teacher) &&
                Objects.equals(price, course.price) &&
                Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, teacher, price, students);
    }
}
