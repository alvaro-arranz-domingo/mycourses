package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.sun.corba.se.spi.activation.Repository;

import java.util.Collection;

/**
 * Created by administrator on 3/12/15.
 */
public class FindAllCoursesUseCase {

    private CourseRepository repository;

    public FindAllCoursesUseCase(CourseRepository courseRepository) {
        this.repository = courseRepository;
    }

    public Collection<Course> execute() {
        return this.repository.findAll();
    }
}
