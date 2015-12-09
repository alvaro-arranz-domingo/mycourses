package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Enrollment;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.secondary.*;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by administrator on 2/12/15.
 */
public class AddEnrollmentUseCase {

    private CourseRepository courseRepository;
    private StudentRepository studentRepository;
    private EnrollmentRepository enrollmentRepository;
    private EmailNotifier emailNotifier;
    private PaymentGateway paymentGateway;

    public AddEnrollmentUseCase(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, StudentRepository studentRepository,
                                EmailNotifier emailNotifier, PaymentGateway paymentGateway) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.emailNotifier = emailNotifier;
        this.paymentGateway = paymentGateway;
    }

    public void execute(Enrollment enrollment, AddEnrollmentResponse response) {

        Optional<Course> course = courseRepository.findById(enrollment.getCourseId());

        if (!course.isPresent()) {
            response.courseNotFound();
            return;
        }

        Optional<Student> student = studentRepository.findById(enrollment.getStudentId());

        if (!student.isPresent()) {
            response.studentNotFound();
            return;
        }

        addStudentToCourse(enrollment, course.get(), student.get(), response);
    }

    private void addStudentToCourse(Enrollment enrollment, Course course, Student student, AddEnrollmentResponse response) {

        Collection<Enrollment> enrollments = enrollmentRepository.findByCourse(course.getId());

        if (course.getCapacity() <= enrollments.size()) {
            response.courseFull();
            return;
        }

        enrollmentRepository.save(enrollment);

        // We book the seat first, then we proceed to payment.
        // To avoid the situation that a student has paid but there is no free seat
        if (!paymentGateway.payCourse(student, course)) {
            enrollmentRepository.remove(enrollment);
            response.paymentFailed();
            return;
        }

        emailNotifier.studentEnrolled(student, course);
        response.enrolled(enrollment);
    }
}
