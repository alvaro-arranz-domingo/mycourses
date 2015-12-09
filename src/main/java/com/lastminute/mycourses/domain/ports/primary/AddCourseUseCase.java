package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;

/**
 * Created by administrator on 7/12/15.
 */
public class AddCourseUseCase {

    private CourseRepository repository;

    public AddCourseUseCase(CourseRepository repository) {
        this.repository = repository;
    }

    public void execute(Course course) {
        repository.save(course);
    }
}
