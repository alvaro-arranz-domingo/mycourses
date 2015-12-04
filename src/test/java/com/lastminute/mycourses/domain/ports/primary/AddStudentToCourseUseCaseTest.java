package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.factory.CourseMother;
import com.lastminute.mycourses.domain.model.factory.StudentMother;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.domain.ports.secondary.EmailNotifier;
import com.lastminute.mycourses.domain.ports.secondary.PaymentGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.AdditionalMatchers.or;
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
    @Mock private EmailNotifier emailNotifier;
    @Mock private PaymentGateway paymentGateway;

    @Mock private AddStudentToCourseRequest request;
    @Mock private AddStudentToCourseResponse response;

    private Long correctCourseId = 1L;
    private Long incorrectCourseId = 0L;
    private Student student = StudentMother.createCorrectTestStudent(1L);
    private Course course = CourseMother.createCorrectTestCourse(correctCourseId);

    private Long fullCourseId = 2L;
    private Course fullCourse = CourseMother.createCorrectTestCourseWithCapacity(fullCourseId, 1);

    @Before
    public void setUp() {

        when(repository.findCourseById(correctCourseId)).thenReturn(Optional.of(course));
        when(repository.findCourseById(fullCourseId)).thenReturn(Optional.of(fullCourse));
        when(repository.findCourseById(not(or(eq(correctCourseId), eq(fullCourseId))))).thenReturn(Optional.<Course>empty());

        when(paymentGateway.payCourse(student, course)).thenReturn(true);

        useCase = new AddStudentToCourseUseCase(repository, emailNotifier, paymentGateway);

        fullCourse.addStudent(student);
    }

    @Test
    public void add_correct_student_to_existent_course() {

        when(request.getCourseId()).thenReturn(correctCourseId);
        when(request.getStudent()).thenReturn(student);

        useCase.execute(request, response);

        verify(emailNotifier).studentEnrolled(student, course);
        verify(paymentGateway).payCourse(student, course);
        verify(response).enrolled(course);
        verifyNoMoreInteractions(response);
    }

    @Test
    public void add_correct_student_to_non_existent_course() {

        when(request.getCourseId()).thenReturn(incorrectCourseId);
        when(request.getStudent()).thenReturn(student);

        useCase.execute(request, response);

        verify(response).courseNotFound();
        verifyNoMoreInteractions(response);
        verifyNoMoreInteractions(emailNotifier, paymentGateway);
    }

    @Test
    public void add_correct_student_to_full_course() {

        when(request.getCourseId()).thenReturn(fullCourseId);
        when(request.getStudent()).thenReturn(student);

        useCase.execute(request, response);

        verify(response).courseFull();
        verifyNoMoreInteractions(response);
        verifyNoMoreInteractions(emailNotifier, paymentGateway);
    }

    @Test
    public void add_correct_student_to_curse_payment_fails() {

        when(request.getCourseId()).thenReturn(correctCourseId);
        when(request.getStudent()).thenReturn(student);
        when(paymentGateway.payCourse(student, course)).thenReturn(Boolean.FALSE);

        useCase.execute(request, response);

        verify(paymentGateway).payCourse(student, course);
        verify(response).paymentFailed();
        verifyNoMoreInteractions(emailNotifier, paymentGateway);

        assertFalse("Student should not be added to the course if payment fails", course.containsStudent(student));
    }
}