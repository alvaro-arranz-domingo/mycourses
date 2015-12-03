package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.Teacher;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.domain.ports.secondary.EmailNotifier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.MailSender;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by administrator on 2/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddStudentToCourseUseCaseTest {

    private AddStudentToCourseUseCase useCase;

    @Mock private CourseRepository repository;
    @Mock private MailSender mailSender;
    @Mock private EmailNotifier emailNotifier;

    private Long correctCourseId = 1L;
    private Long incorrectCourseId = 0L;
    private Student student = new Student("nameTest", "emailTest@gmail.com");
    private Course course = new Course(correctCourseId, "TDD", "TDD cycle. Mocks and stubs.", new Teacher("Teacher name"), BigDecimal.ONE);

    @Before
    public void setUp() {

        when(repository.findCourseById(correctCourseId)).thenReturn(Optional.of(course));
        when(repository.findCourseById(not(eq(correctCourseId)))).thenReturn(Optional.<Course>empty());

        useCase = new AddStudentToCourseUseCase(repository, emailNotifier);
    }

    @Test
    public void add_correct_student_to_existent_course() {

        Optional<Course> course = useCase.execute(correctCourseId, student);

        assertNotNull("Course returned was null", course);
        assertTrue("Course does not contain student", course.get().containsStudent(student));

        verify(emailNotifier).studentEnrolled(student, course.get());
    }

    @Test
    public void add_correct_student_to_non_existent_course() {

        Optional<Course> course = useCase.execute(incorrectCourseId, student);

        assertEquals("Course was returned when incorrect course id", Optional.empty(), course);
        verifyNoMoreInteractions(emailNotifier, mailSender);
    }
}