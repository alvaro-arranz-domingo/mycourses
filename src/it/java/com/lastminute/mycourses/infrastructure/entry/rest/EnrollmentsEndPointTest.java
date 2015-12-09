package com.lastminute.mycourses.infrastructure.entry.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.util.DummySSLSocketFactory;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.lastminute.mycourses.Application;
import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Enrollment;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.factory.CourseMother;
import com.lastminute.mycourses.domain.model.factory.StudentMother;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.domain.ports.secondary.EnrollmentRepository;
import com.lastminute.mycourses.domain.ports.secondary.StudentRepository;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapCourseRepository;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapEnrollmentRepository;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapStudentRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.mail.Message;
import java.security.Security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by administrator on 2/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestPropertySource(locations="classpath:it.properties")
@WebAppConfiguration
public class EnrollmentsEndPointTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private VolatileMapCourseRepository courseRepository;

    @Autowired
    private VolatileMapStudentRepository studentRepository;

    @Autowired
    private VolatileMapEnrollmentRepository enrollmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private GreenMail greenMailSmtp;

    private Long correctCourseId = 1L;
    private Long incorrectCourseId = 0L;
    private Course course = CourseMother.createCorrectTestCourseForPayment(correctCourseId);

    private Long incorrectStudentId = 0L;
    private Long studentCorrectVisaId = 1L;
    private Student studentCorrectVisa = StudentMother.createCorrectTestStudentForPayment(studentCorrectVisaId);
    private Student studentIncorrectVisa = StudentMother.createCorrectTestStudent(2L);

    private Long fullCourseId = 2L;
    private Course fullCourse = CourseMother.createCorrectTestCourseWithCapacity(fullCourseId, 1);

    private Enrollment correctEnrollment = new Enrollment(1L, correctCourseId, studentCorrectVisaId);
    private Enrollment enrollmentNonExistentCourse = new Enrollment(2L, incorrectCourseId, studentCorrectVisaId);
    private Enrollment enrollmentNonExistentStudent = new Enrollment(3L, correctCourseId, incorrectStudentId);
    private Enrollment fullCourseEnrollment = new Enrollment(4L, fullCourseId, studentCorrectVisaId);
    private Enrollment incorrectVisaEnrollment = new Enrollment(5L, correctCourseId, studentIncorrectVisa.getId());

    @Before
    public void setUp() {

        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        greenMailSmtp = new GreenMail(ServerSetupTest.SMTPS);
        greenMailSmtp.start();

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        enrollmentRepository.save(new Enrollment(100L, fullCourseId, studentCorrectVisa.getId()));

        courseRepository.save(course);
        courseRepository.save(fullCourse);

        studentRepository.save(studentCorrectVisa);
        studentRepository.save(studentIncorrectVisa);
    }

    @Test
    public void add_correct_enrollment() throws Exception {

        mockMvc.perform(
                post("/api/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(correctEnrollment)))
                .andExpect(status().isCreated());

        Message[] messages = greenMailSmtp.getReceivedMessages();
        assertThat("Wrong number of emails sent", messages.length, equalTo(1));
        assertThat("Wrong email recipient", studentCorrectVisa.getEmailAddress(), equalTo(messages[0].getAllRecipients()[0].toString()));
    }

    @Test
    public void add_incorrect_json_enrollment() throws Exception {

        mockMvc.perform(
                post("/api/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("")))
                .andExpect(status().isBadRequest());

        Message[] messages = greenMailSmtp.getReceivedMessages();
        assertThat("Wrong number of emails sent", messages.length, equalTo(0));
    }

    @Test
    public void add_correct_enrollment_to_non_existent_course() throws Exception {

        mockMvc.perform(
                post("/api/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(enrollmentNonExistentCourse)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void add_correct_enrollment_to_non_existent_student() throws Exception {

        mockMvc.perform(
                post("/api/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollmentNonExistentStudent)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void add_correct_student_to_full_course() throws Exception {

        mockMvc.perform(
                post("/api/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fullCourseEnrollment)))
                .andExpect(status().isConflict());

        Message[] messages = greenMailSmtp.getReceivedMessages();
        assertThat("A confirmation email was send when subscribing a student to a full course", messages.length, equalTo(0));
    }

    @Test
    public void add_correct_student_incorrect_visa_to_existent_course() throws Exception {

        mockMvc.perform(
                post("/api/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incorrectVisaEnrollment)))
                .andExpect(status().isConflict());

        Message[] messages = greenMailSmtp.getReceivedMessages();
        assertThat("A confirmation email was send when subscribing a student to a full course", messages.length, equalTo(0));
    }

    @After
    public void tearDown() {
        greenMailSmtp.stop();

        courseRepository.clear();
        studentRepository.clear();
        enrollmentRepository.clear();
    }
}
