package com.lastminute.mycourses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.Teacher;
import com.lastminute.mycourses.domain.ports.primary.AddStudentToCourseUseCase;
import com.lastminute.mycourses.domain.ports.primary.FindCourseUseCase;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.infrastructure.entry.rest.serialization.CourseMixIn;
import com.lastminute.mycourses.infrastructure.entry.rest.serialization.StudentMixIn;
import com.lastminute.mycourses.infrastructure.entry.rest.serialization.TeacherMixIn;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapCourseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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
    AddStudentToCourseUseCase getAddStudentToCourseUseCase(CourseRepository courseRepository) {
        return new AddStudentToCourseUseCase(courseRepository);
    }

}
