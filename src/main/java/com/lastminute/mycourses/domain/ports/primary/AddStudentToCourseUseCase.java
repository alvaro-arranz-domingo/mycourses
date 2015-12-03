package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.domain.ports.secondary.EmailNotifier;

import java.util.Optional;

/**
 * Created by administrator on 2/12/15.
 */
public class AddStudentToCourseUseCase {

    private CourseRepository repository;
    private EmailNotifier emailNotifier;

    public AddStudentToCourseUseCase(CourseRepository repository, EmailNotifier emailNotifier) {
        this.repository = repository;
        this.emailNotifier = emailNotifier;
    }

    public void execute(AddStudentToCourseRequest request, AddStudentToCourseResponse response) {

        Optional<Course> course = repository.findCourseById(request.getCourseId());

        if (!course.isPresent()) {
            response.isCourseNotFound();
            return;
        }

        if (!course.get().addStudent(request.getStudent())) {
            response.isFull();
            return;
        }

        response.isOk(course.get());
        emailNotifier.studentEnrolled(request.getStudent(), course.get());
    }
}
