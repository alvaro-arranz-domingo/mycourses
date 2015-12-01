package com.lastminute.mycourses.domain.ports.secondary;

import com.lastminute.mycourses.domain.model.Course;

import java.util.Optional;

/**
 * Created by administrator on 1/12/15.
 */
public interface CourseRepository {

    Optional<Course> findCourseById(Long id);
}
