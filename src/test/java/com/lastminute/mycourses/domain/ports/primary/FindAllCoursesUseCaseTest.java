package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Teacher;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapCourseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by administrator on 3/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class FindAllCoursesUseCaseTest {

    private FindAllCoursesUseCase useCase;

    @Mock
    private CourseRepository repository;

    private Long course1Id = 0L;
    private Long course2Id = 1L;

    private Course course1 = new Course(course1Id, "TDD", "TDD cycle. Mocks and stubs.", new Teacher("Teacher name"), BigDecimal.ONE, 20);
    private Course course2 = new Course(course2Id, "TDD", "TDD cycle. Mocks and stubs.", new Teacher("Teacher name"), BigDecimal.ONE, 20);

    private Collection<Course> expectedCourses = new ArrayList<Course>();

    @Before
    public void setUp() {

        expectedCourses.add(course1);
        expectedCourses.add(course2);

        useCase = new FindAllCoursesUseCase(repository);

        when(repository.findAll()).thenReturn(expectedCourses);
    }

    @Test
    public void get_all_courses() {

        Collection<Course> courses = useCase.execute();

        assertThat("Wrong courses returned", courses, equalTo(expectedCourses));
    }
}