package com.lastminute.mycourses;

import com.lastminute.mycourses.domain.ports.primary.AddStudentToCourseUseCase;
import com.lastminute.mycourses.domain.ports.primary.FindAllCoursesUseCase;
import com.lastminute.mycourses.domain.ports.primary.FindCourseUseCase;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.domain.ports.secondary.EmailNotifier;
import com.lastminute.mycourses.domain.ports.secondary.PaymentGateway;
import com.lastminute.mycourses.infrastructure.notifier.email.SimpleEmailNotifier;
import com.lastminute.mycourses.infrastructure.payment.StripePaymentGateway;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapCourseRepository;
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
    CourseRepository getCourseRepository() {
        return new VolatileMapCourseRepository();
    }

    @Bean
    FindCourseUseCase getFindCourseUserCase(CourseRepository courseRepository) {
        return new FindCourseUseCase(courseRepository);
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
    AddStudentToCourseUseCase getAddStudentToCourseUseCase(CourseRepository courseRepository, EmailNotifier emailNotifier, PaymentGateway paymentGateway) {
        return new AddStudentToCourseUseCase(courseRepository, emailNotifier, paymentGateway);
    }

    @Bean
    FindAllCoursesUseCase getFindAllCoursesUseCase(CourseRepository courseRepository) {
        return new FindAllCoursesUseCase(courseRepository);
    }

}
