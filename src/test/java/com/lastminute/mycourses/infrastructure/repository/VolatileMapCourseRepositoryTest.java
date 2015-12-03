package com.lastminute.mycourses.infrastructure.repository;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Teacher;
import com.lastminute.mycourses.domain.model.factory.CourseMother;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by administrator on 1/12/15.
 */
public class VolatileMapCourseRepositoryTest {

    private VolatileMapCourseRepository courseRepository;

    private Long validCourseId = 1L;
    private Long missingCourseId = 0l;
    private Long otherValidCourseId = 2L;

    private Course validCourse = CourseMother.createCorrectTestCourse(validCourseId);
    private Course otherValidCourse = CourseMother.createCorrectTestCourse(otherValidCourseId);

    @Before
    public void setUp() {

        courseRepository = new VolatileMapCourseRepository();

        courseRepository.save(validCourse);
        courseRepository.save(otherValidCourse);
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

    @Test
    public void find_all_courses() {

        Collection<Course> courses = courseRepository.findAll();

        assertNotNull("Returned course collection is null", courses);
        assertThat("Returned wrong number of courses", courses.size(), equalTo(2));
        assertTrue("Returned wrong collection of courses", courses.contains(validCourse));
        assertTrue("Returned wrong collection of courses", courses.contains(otherValidCourse));
    }

}