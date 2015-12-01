package com.lastminute.mycourses.infrastructure.repository;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by administrator on 1/12/15.
 */
public class VolatileMapCourseRepository implements CourseRepository {

    private Map<Long, Course> courseMap;

    public VolatileMapCourseRepository() {
        courseMap = new HashMap<Long, Course>();
    }

    @Override
    public Optional<Course> findCourseById(Long id) {
        Course course = courseMap.get(id);

        if (course != null) {
            return Optional.of(course);
        }

        return Optional.empty();
    }

    public void save(Course course) {
        courseMap.put(course.getId(), course);
    }
}
