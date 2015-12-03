package com.lastminute.mycourses.infrastructure.email;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.Teacher;
import com.lastminute.mycourses.domain.model.factory.CourseMother;
import com.lastminute.mycourses.domain.model.factory.StudentMother;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by administrator on 3/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailNotifierTest {

    private SimpleEmailNotifier simpleEmailNotifier;

    @Mock private MailSender mailSender;

    private Student student = StudentMother.createCorrectTestStudent();
    private Course course = CourseMother.createCorrectTestCourse(0L);

    @Before
    public void setUp() {

        simpleEmailNotifier = new SimpleEmailNotifier(mailSender);
    }

    @Test
    public void enroll_student_email_is_correctly_sent() {

        simpleEmailNotifier.studentEnrolled(student, course);
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

}