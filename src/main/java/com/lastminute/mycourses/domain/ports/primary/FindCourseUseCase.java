package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;

import java.util.Optional;

/**
 * Created by administrator on 1/12/15.
 */
public class FindCourseUseCase {

    private CourseRepository courseRepository;

    public FindCourseUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Optional<Course> execute(Long id) {
        return  courseRepository.findById(id);
    }
}