package com.lastminute.mycourses;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.mycourses.domain.model.*;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.domain.ports.secondary.StudentRepository;
import com.lastminute.mycourses.infrastructure.entry.rest.serialization.*;
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
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    void configureObjectMapper() {
        objectMapper.addMixIn(Course.class, CourseMixIn.class);
        objectMapper.addMixIn(Teacher.class, TeacherMixIn.class);
        objectMapper.addMixIn(Student.class, StudentMixIn.class);
        objectMapper.addMixIn(VisaCard.class, VisaCardMixin.class);
        objectMapper.addMixIn(Month.class, MonthMixIn.class);
        objectMapper.addMixIn(Year.class, YearMixIn.class);
        objectMapper.addMixIn(Enrollment.class, EnrollmentMixIn.class);
    }

    @Override
    public void run(String... args) throws Exception {
        courseRepository.save(new Course(100L, "Integration Course", "Test course", new Teacher("TestTeacher"), BigDecimal.ZERO, 20));
        courseRepository.save(new Course(101L, "Maths", "101 on Maths", new Teacher("Alfredo"), BigDecimal.ONE, 3));

        VisaCard visaCard = new VisaCard(4242424242424242L, new Month(12), new Year(2016), 314);
        studentRepository.save(new Student(200L, "Álvaro Arranz Domingo", "alvaro.arranz.domingo@gmail.com", visaCard));
    }
}
