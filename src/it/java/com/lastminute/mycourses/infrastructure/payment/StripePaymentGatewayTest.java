package com.lastminute.mycourses.infrastructure.payment;

import com.lastminute.mycourses.Application;
import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.factory.CourseMother;
import com.lastminute.mycourses.domain.model.factory.StudentMother;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by administrator on 4/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestPropertySource(locations="classpath:it.properties")
@WebAppConfiguration
public class StripePaymentGatewayTest {

    @Autowired
    private StripePaymentGateway paymentGateway;

    private Course course = CourseMother.createCorrectTestCourseForPayment(0L);
    private Student studentCorrectVisa = StudentMother.createCorrectTestStudentForPayment(1L);
    private Student studentIncorrectVisa = StudentMother.createCorrectTestStudent(2L);

    @Test
    public void student_pays_course_with_correct_visa() {

        boolean success = paymentGateway.payCourse(studentCorrectVisa, course);

        assertTrue("Payment failed", success);
    }

    @Test
    public void student_pays_course_with_incorrect_visa() {

        boolean success = paymentGateway.payCourse(studentIncorrectVisa, course);

        assertFalse("Wrong payment actually worked", success);
    }
}
