package com.lastminute.mycourses.infrastructure.repository;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by administrator on 1/12/15.
 */
public class VolatileMapCourseRepository extends  VolatileMapRepository<Course> implements CourseRepository {

    public void save(Course course) {
        save(course.getId(), course);
    }

    public void remove(Course course) {
        remove(course.getId());
    }
}
