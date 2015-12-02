package com.lastminute.mycourses;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Teacher;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

/**
 * Created by administrator on 1/12/15.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        VolatileMapCourseRepository volatileMapCourseRepository = (VolatileMapCourseRepository) courseRepository;
        volatileMapCourseRepository.save(new Course(100L, "Integration Course", "Test course", new Teacher("TestTeacher"), BigDecimal.ZERO));
    }
}
