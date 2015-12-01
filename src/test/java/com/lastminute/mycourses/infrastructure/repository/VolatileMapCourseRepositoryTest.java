package com.lastminute.mycourses.infrastructure.repository;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Teacher;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by administrator on 1/12/15.
 */
public class VolatileMapCourseRepositoryTest {

    private VolatileMapCourseRepository courseRepository;

    private Course validCourse;
    private Long validCourseId = 1L;
    private Long missingCourseId = 0l;
    private String validCourseName = "TestName";
    private String validCourseDescription = "TestDescription";
    private BigDecimal validCoursePrice = BigDecimal.ONE;

    @Before
    public void setUp() {

        courseRepository = new VolatileMapCourseRepository();

        validCourse = createCourse();
        courseRepository.save(validCourse);
    }

    private Course createCourse() {
        Teacher teacher = new Teacher("TestTeacherName");
        return new Course(validCourseId, validCourseName, validCourseDescription, teacher, validCoursePrice);
    }

    @Test
    public void save_and_find_a_valid_course() {

        Optional<Course> optionalCourse = courseRepository.findCourseById(validCourse.getId());

        assertEquals("Course", Optional.of(validCourse), optionalCourse);
    }

    @Test
    public void find_non_existent_course() {

        Optional<Course> optionalCourse = courseRepository.findCourseById(missingCourseId);

        assertEquals("Course", Optional.empty(), optionalCourse);
    }

}