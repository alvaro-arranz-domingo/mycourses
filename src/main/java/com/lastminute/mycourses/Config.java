package com.lastminute.mycourses;

import com.lastminute.mycourses.domain.ports.primary.AddStudentToCourseUseCase;
import com.lastminute.mycourses.domain.ports.primary.FindCourseUseCase;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.domain.ports.secondary.EmailNotifier;
import com.lastminute.mycourses.infrastructure.email.SimpleEmailNotifier;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapCourseRepository;
import org.hibernate.validator.constraints.Email;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

/**
 * Created by administrator on 1/12/15.
 */
@Configuration
public class Config {

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
    AddStudentToCourseUseCase getAddStudentToCourseUseCase(CourseRepository courseRepository, EmailNotifier emailNotifier) {
        return new AddStudentToCourseUseCase(courseRepository, emailNotifier);
    }

}
