package com.lastminute.mycourses.infrastructure.entry.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.mycourses.Application;
import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.factory.StudentMother;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by administrator on 7/12/15.
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
    private VolatileMapStudentRepository studentRepository;

    @Autowired
    private ObjectMapper mapper;

    private Student student = StudentMother.createCorrectTestStudent(1L);
    private Student studentOther1 = StudentMother.createCorrectTestStudent(2L);
    private Student studentOther2 = StudentMother.createCorrectTestStudent(3L);

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        studentRepository.save(studentOther1);
        studentRepository.save(studentOther2);
    }

    @Test
    public void add_new_correct_student() throws Exception {

        mockMvc.perform(post("/api/students")
                .content(mapper.writeValueAsString(student))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void add_new_incorrect_json_student() throws Exception {

        mockMvc.perform(post("/api/students")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void get_all_students() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/students")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JavaType type = mapper.getTypeFactory().
                constructCollectionType(Collection.class, Student.class);

        Collection<Student> students = mapper.readValue(result.getResponse().getContentAsString(), type);
        Collection<Long> studentsIds = students.stream().map(x -> x.getId()).collect(Collectors.toCollection(ArrayList::new));

        assertTrue("Student is not returned", studentsIds.contains(2L));
        assertTrue("Student is not returned", studentsIds.contains(3L));
    }

    @After
    public void tearDown() {
        studentRepository.clear();
    }
}
