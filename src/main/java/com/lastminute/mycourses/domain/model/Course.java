package com.lastminute.mycourses.domain.model;

import java.math.BigDecimal;

/**
 * Created by administrator on 1/12/15.
 */
public class Course {

    private Long id;
    private String name;
    private String description;
    private Teacher teacher;
    private BigDecimal price;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != null ? !id.equals(course.id) : course.id != null) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;
        if (description != null ? !description.equals(course.description) : course.description != null) return false;
        if (teacher != null ? !teacher.equals(course.teacher) : course.teacher != null) return false;
        return !(price != null ? !price.equals(course.price) : course.price != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
