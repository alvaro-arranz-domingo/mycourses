package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Teacher;
import com.lastminute.mycourses.domain.model.factory.CourseMother;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by administrator on 1/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class FindCourseUseCaseTest {

    private FindCourseUseCase useCase;

    @Mock private CourseRepository courseRepository;

    private Teacher teacher = new Teacher("Teacher name");
    private Course expectedCourse = CourseMother.createCorrectTestCourse(1L);

    private Long existingId = 1L;
    private Long missingId = 2l;

    @Before
    public void setUp() {
        when(courseRepository.findById(existingId)).thenReturn(Optional.of(expectedCourse));
        when(courseRepository.findById(not(eq(existingId)))).thenReturn(Optional.<Course>empty());

        useCase = new FindCourseUseCase(courseRepository);
    }

    @Test
    public void get_existent_course() {

        Optional<Course> course = useCase.execute(existingId);

        assertEquals("Course", Optional.of(expectedCourse), course);
    }

    @Test
    public void get_non_existent_course() {

        Optional<Course> course = useCase.execute(missingId);

        assertEquals("Course", Optional.empty(), course);
    }
}