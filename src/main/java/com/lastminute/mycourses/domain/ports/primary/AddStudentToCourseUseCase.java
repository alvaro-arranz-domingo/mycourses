package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.domain.ports.secondary.EmailNotifier;
import com.lastminute.mycourses.domain.ports.secondary.PaymentGateway;

import java.util.Optional;

/**
 * Created by administrator on 2/12/15.
 */
public class AddStudentToCourseUseCase {

    private CourseRepository repository;
    private EmailNotifier emailNotifier;
    private PaymentGateway paymentGateway;

    public AddStudentToCourseUseCase(CourseRepository repository, EmailNotifier emailNotifier, PaymentGateway paymentGateway) {
        this.repository = repository;
        this.emailNotifier = emailNotifier;
        this.paymentGateway = paymentGateway;
    }

    public void execute(AddStudentToCourseRequest request, AddStudentToCourseResponse response) {

        Optional<Course> course = repository.findCourseById(request.getCourseId());

        if (!course.isPresent()) {
            response.courseNotFound();
            return;
        }

        addStudentToCourse(request.getStudent(), course.get(), response);
    }

    private void addStudentToCourse(Student student, Course course, AddStudentToCourseResponse response) {
        if (!course.addStudent(student)) {
            response.courseFull();
            return;
        }

        // We book the seat first, then we proceed to payment.
        // To avoid the situation that a student has paid but there is no free seat
        if (!paymentGateway.payCourse(student, course)) {
            course.removeStudent(student);
            response.paymentFailed();
            return;
        }

        emailNotifier.studentEnrolled(student, course);
        response.enrolled(course);
    }
}
