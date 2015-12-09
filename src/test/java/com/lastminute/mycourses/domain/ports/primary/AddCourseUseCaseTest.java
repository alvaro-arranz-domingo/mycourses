package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.factory.CourseMother;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by administrator on 7/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddCourseUseCaseTest {

    private AddCourseUseCase useCase;

    @Mock private CourseRepository repository;

    private Course course = CourseMother.createCorrectTestCourse(0L);

    @Before
    public void setUp() {
        useCase =  new AddCourseUseCase(repository);
    }

    @Test
    public void save_correct_course() {

        useCase.execute(course);

        verify(repository).save(course);
    }
}