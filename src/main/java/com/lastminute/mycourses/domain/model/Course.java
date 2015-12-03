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
    private int capacity;

    public Course(Long id, String name, String description, Teacher teacher, BigDecimal price, int capacity) {
        this.capacity = capacity;
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

    /**
     * Checks if there is enough capacity in the Course. If there is not, it returns false
     * @param student
     * @return
     */
    public boolean addStudent(Student student) {

        if (students.size() >= capacity) {
            return false;
        }

        students.add(student);
        return true;
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public boolean containsStudent(Student student) {
        return students.contains(student);
    }

    public int getCapacity() {
        return capacity;
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
