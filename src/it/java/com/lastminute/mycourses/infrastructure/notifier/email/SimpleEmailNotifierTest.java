package com.lastminute.mycourses.infrastructure.notifier.email;

import com.icegreen.greenmail.util.DummySSLSocketFactory;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.lastminute.mycourses.Application;
import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.factory.CourseMother;
import com.lastminute.mycourses.domain.model.factory.StudentMother;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.security.Security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by administrator on 3/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestPropertySource(locations="classpath:it.properties")
@WebAppConfiguration
public class SimpleEmailNotifierTest {

    @Autowired
    private SimpleEmailNotifier emailNotifier;

    private GreenMail greenMailSmtp;

    private Course course = CourseMother.createCorrectTestCourse(0L);
    private Student student = StudentMother.createCorrectTestStudent(1L);

    @Before
    public void setUp() {

        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        greenMailSmtp = new GreenMail(ServerSetupTest.SMTPS);
        greenMailSmtp.start();
    }

    @Test
    public void send_student_enrolled_email() throws MessagingException {

        emailNotifier.studentEnrolled(student, course);

        Message[] messages = greenMailSmtp.getReceivedMessages();
        assertThat("Wrong number of emails sent", messages.length, equalTo(1));
        assertThat("Wrong email recipient", student.getEmailAddress(), equalTo(messages[0].getAllRecipients()[0].toString()));
    }

    @After
    public void tearDown() {
        greenMailSmtp.stop();
    }

}
