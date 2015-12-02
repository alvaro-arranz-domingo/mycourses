package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;

import java.util.Optional;

/**
 * Created by administrator on 2/12/15.
 */
public class AddStudentToCourseUseCase {

    private CourseRepository repository;

    public AddStudentToCourseUseCase(CourseRepository repository) {
        this.repository = repository;
    }

    public Course execute(Long courseId, Student student) {

        Optional<Course> courseOptional = repository.findCourseById(courseId);

        if (courseOptional.isPresent()) {
            courseOptional.get().addStudent(student);
            return  courseOptional.get();
        }

        return null;
    }
}
