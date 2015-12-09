package com.lastminute.mycourses;

import com.lastminute.mycourses.domain.ports.primary.*;
import com.lastminute.mycourses.domain.ports.secondary.*;
import com.lastminute.mycourses.infrastructure.notifier.email.SimpleEmailNotifier;
import com.lastminute.mycourses.infrastructure.payment.StripePaymentGateway;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapCourseRepository;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapEnrollmentRepository;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapStudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

/**
 * Created by administrator on 1/12/15.
 */
@Configuration
public class Config {

    @Value("${paymentgateway.stripe.apikey}")
    private String stripeApiKey;

    @Bean
    StudentRepository getStudentRepository() {
        return new VolatileMapStudentRepository();
    }

    @Bean
    CourseRepository getCourseRepository() {
        return new VolatileMapCourseRepository();
    }

    @Bean
    EnrollmentRepository getEnrollmentRepository() {
        return new VolatileMapEnrollmentRepository();
    }

    @Bean
    FindCourseUseCase getFindCourseUserCase(CourseRepository courseRepository) {
        return new FindCourseUseCase(courseRepository);
    }

    @Bean
    AddCourseUseCase getAddCourseUseCase(CourseRepository courseRepository) {
        return new AddCourseUseCase(courseRepository);
    }

    @Bean
    FindAllStudentsUseCase findAllStudentsUseCase(StudentRepository studentRepository) {
        return new FindAllStudentsUseCase(studentRepository);
    }

    @Bean
    EmailNotifier getEmailNotifier(MailSender mailSender) {
        return new SimpleEmailNotifier(mailSender);
    }

    @Bean
    PaymentGateway getPaymentGateway() {
        return new StripePaymentGateway(stripeApiKey);
    }

    @Bean
    AddEnrollmentUseCase getAddStudentToCourseUseCase(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository,
                                                      StudentRepository studentRepository, EmailNotifier emailNotifier, PaymentGateway paymentGateway) {
        return new AddEnrollmentUseCase(enrollmentRepository, courseRepository, studentRepository, emailNotifier, paymentGateway);
    }

    @Bean
    FindAllCoursesUseCase getFindAllCoursesUseCase(CourseRepository courseRepository) {
        return new FindAllCoursesUseCase(courseRepository);
    }

    @Bean
    AddStudentUseCase getAddStudentUseCase(StudentRepository studentRepository) {
        return new AddStudentUseCase(studentRepository);
    }

}
