package com.lastminute.mycourses;

import com.lastminute.mycourses.domain.ports.primary.FindCourseUseCase;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapCourseRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
