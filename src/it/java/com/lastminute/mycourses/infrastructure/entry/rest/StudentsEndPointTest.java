package com.lastminute.mycourses.infrastructure.entry.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icegreen.greenmail.util.DummySSLSocketFactory;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.lastminute.mycourses.Application;
import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.factory.CourseMother;
import com.lastminute.mycourses.domain.model.factory.StudentMother;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapCourseRepository;
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
import static org.hamcrest.Matchers.equalTo;
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
public class StudentsEndPointTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private VolatileMapCourseRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private GreenMail greenMailSmtp;

    private Long correctCourseId = 1L;
    private Long incorrectCourseId = 0L;
    private Course course = CourseMother.createCorrectTestCourseForPayment(correctCourseId);

    private Student studentCorrectVisa = StudentMother.createCorrectTestStudentForPayment(1L);
    private Student studentIncorrectVisa = StudentMother.createCorrectTestStudent(2L);

    private Long fullCourseId = 2L;
    private Course fullCourse = CourseMother.createCorrectTestCourseWithCapacity(fullCourseId, 1);

    @Before
    public void setUp() {

        Security.setProperty("ssl.SocketFactory.provider", DummySSLSocketFactory.class.getName());
        greenMailSmtp = new GreenMail(ServerSetupTest.SMTPS);
        greenMailSmtp.start();

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        fullCourse.addStudent(studentCorrectVisa);

        repository.save(course);
        repository.save(fullCourse);
    }

    @Test
    public void add_correct_student_to_existent_course() throws Exception {

        mockMvc.perform(
                post("/api/courses/" + correctCourseId + "/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentCorrectVisa)))
                .andExpect(status().isOk());

        Message[] messages = greenMailSmtp.getReceivedMessages();
        assertThat("Wrong number of emails sent", messages.length, equalTo(1));
        assertThat("Wrong email recipient", studentCorrectVisa.getEmailAddress(), equalTo(messages[0].getAllRecipients()[0].toString()));
    }

    @Test
    public void add_correct_student_to_non_existent_course() throws Exception {

        mockMvc.perform(
                post("/api/courses/" + incorrectCourseId + "/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentCorrectVisa)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void add_non_json_student_to_existent_course() throws Exception {

        mockMvc.perform(
                post("/api/courses/" + correctCourseId + "/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void add_correct_student_to_full_course() throws Exception {

        mockMvc.perform(
                post("/api/courses/" + fullCourseId + "/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentCorrectVisa)))
                .andExpect(status().isConflict());

        Message[] messages = greenMailSmtp.getReceivedMessages();
        assertThat("A confirmation email was send when subscribing a student to a full course", messages.length, equalTo(0));
    }

    @Test
    public void add_correct_student_incorrect_visa_to_existent_course() throws Exception {

        mockMvc.perform(
                post("/api/courses/" + fullCourseId + "/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentIncorrectVisa)))
                .andExpect(status().isConflict());

        Message[] messages = greenMailSmtp.getReceivedMessages();
        assertThat("A confirmation email was send when subscribing a student to a full course", messages.length, equalTo(0));
    }

    @After
    public void tearDown() {
        greenMailSmtp.stop();

        repository.remove(course);
        repository.remove(fullCourse);
    }

}
