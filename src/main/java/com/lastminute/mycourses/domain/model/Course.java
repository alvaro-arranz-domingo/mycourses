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
}
