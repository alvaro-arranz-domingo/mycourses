package com.lastminute.mycourses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.Teacher;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.infrastructure.entry.rest.serialization.CourseMixIn;
import com.lastminute.mycourses.infrastructure.entry.rest.serialization.StudentMixIn;
import com.lastminute.mycourses.infrastructure.entry.rest.serialization.TeacherMixIn;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * Created by administrator on 1/12/15.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        VolatileMapCourseRepository volatileMapCourseRepository = (VolatileMapCourseRepository) courseRepository;
        volatileMapCourseRepository.save(new Course(100L, "Integration Course", "Test course", new Teacher("TestTeacher"), BigDecimal.ZERO, 20));
        volatileMapCourseRepository.save(new Course(101L, "Maths", "101 on Maths", new Teacher("Alfredo"), BigDecimal.ONE, 3));
    }

    @PostConstruct
    void configureObjectMapper() {
        objectMapper.addMixIn(Course.class, CourseMixIn.class);
        objectMapper.addMixIn(Teacher.class, TeacherMixIn.class);
        objectMapper.addMixIn(Student.class, StudentMixIn.class);
    }
}
