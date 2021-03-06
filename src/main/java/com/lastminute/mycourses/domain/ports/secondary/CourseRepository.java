package com.lastminute.mycourses.domain.ports.secondary;

import com.lastminute.mycourses.domain.model.Course;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by administrator on 1/12/15.
 */
public interface CourseRepository {

    Optional<Course> findById(Long id);

    Collection<Course> findAll();

    void save(Course course);
}
