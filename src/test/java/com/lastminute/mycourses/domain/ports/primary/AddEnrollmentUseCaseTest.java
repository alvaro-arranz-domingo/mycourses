package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Enrollment;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.factory.CourseMother;
import com.lastminute.mycourses.domain.model.factory.StudentMother;
import com.lastminute.mycourses.domain.ports.secondary.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by administrator on 2/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddEnrollmentUseCaseTest {

    private AddEnrollmentUseCase useCase;

    @Mock private CourseRepository courseRepository;
    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private StudentRepository studentRepository;
    @Mock private EmailNotifier emailNotifier;
    @Mock private PaymentGateway paymentGateway;

    @Mock private AddEnrollmentResponse response;

    private Long correctCourseId = 1L;
    private Long incorrectCourseId = 0L;
    private Course course = CourseMother.createCorrectTestCourse(correctCourseId);

    private Long correctStudentId = 1L;
    private Long incorrectStudentId = 0L;
    private Student student = StudentMother.createCorrectTestStudent(correctStudentId);

    private Enrollment correctEnrollment = new Enrollment(0L, correctCourseId, correctStudentId);
    private Enrollment incorrectCourseEnrollment = new Enrollment(1L, incorrectCourseId, correctStudentId);
    private Enrollment incorrectStudentEnrollment = new Enrollment(2L, correctCourseId, incorrectStudentId);

    private Long fullCourseId = 2L;
    private Course fullCourse = CourseMother.createCorrectTestCourseWithCapacity(fullCourseId, 1);
    private Enrollment fullCourseEnrollment = new Enrollment(3L, fullCourseId, correctStudentId);

    @Before
    public void setUp() {

        when(courseRepository.findById(correctCourseId)).thenReturn(Optional.of(course));
        when(courseRepository.findById(fullCourseId)).thenReturn(Optional.of(fullCourse));
        when(courseRepository.findById(not(or(eq(correctCourseId), eq(fullCourseId))))).thenReturn(Optional.<Course>empty());

        when(studentRepository.findById(correctStudentId)).thenReturn(Optional.of(student));
        when(studentRepository.findById(not(eq(correctStudentId)))).thenReturn(Optional.<Student>empty());

        when(enrollmentRepository.findByCourse(correctCourseId)).thenReturn(new ArrayList<Enrollment>());
        when(enrollmentRepository.findByStudent(correctStudentId)).thenReturn(new ArrayList<Enrollment>());

        Collection<Enrollment> fullCourseEnrollments = new ArrayList<>();
        fullCourseEnrollments.add(new Enrollment(0L, fullCourseId, correctStudentId));

        when(enrollmentRepository.findByCourse(fullCourseId)).thenReturn(fullCourseEnrollments);

        when(paymentGateway.payCourse(student, course)).thenReturn(true);

        useCase = new AddEnrollmentUseCase(enrollmentRepository, courseRepository, studentRepository, emailNotifier, paymentGateway);
    }

    @Test
    public void add_correct_student_to_existent_course() {

        useCase.execute(correctEnrollment, response);

        verify(enrollmentRepository).save(correctEnrollment);
        verify(emailNotifier).studentEnrolled(student, course);
        verify(paymentGateway).payCourse(student, course);
        verify(response).enrolled(correctEnrollment);
        verifyNoMoreInteractions(response);
    }

    @Test
    public void add_correct_student_to_non_existent_course() {

        useCase.execute(incorrectCourseEnrollment, response);

        verify(response).courseNotFound();
        verifyNoMoreInteractions(response);
        verifyNoMoreInteractions(emailNotifier, paymentGateway);
    }

    @Test
    public void add_incorrect_student_to_existent_course() {

        useCase.execute(incorrectStudentEnrollment, response);

        verify(response).studentNotFound();
        verifyNoMoreInteractions(response);
        verifyNoMoreInteractions(emailNotifier, paymentGateway);
    }

    @Test
    public void add_correct_student_to_full_course() {

        useCase.execute(fullCourseEnrollment, response);

        verify(response).courseFull();
        verify(enrollmentRepository, never()).save(any(Enrollment.class));
        verifyNoMoreInteractions(response, emailNotifier, paymentGateway);
    }

    @Test
    public void add_correct_student_to_curse_payment_fails() {

        when(paymentGateway.payCourse(student, course)).thenReturn(Boolean.FALSE);

        useCase.execute(correctEnrollment, response);

        verify(paymentGateway).payCourse(student, course);
        verify(response).paymentFailed();
        verifyNoMoreInteractions(emailNotifier, paymentGateway);

        //assertFalse("Student should not be added to the course if payment fails", course.containsStudent(student));
    }
}