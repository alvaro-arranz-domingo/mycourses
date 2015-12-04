package com.lastminute.mycourses.infrastructure.entry.rest;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastminute.mycourses.Application;
import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Teacher;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.infrastructure.repository.VolatileMapCourseRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by administrator on 1/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestPropertySource(locations="classpath:it.properties")
@WebAppConfiguration
public class CoursesEndPointTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private VolatileMapCourseRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long correctId = 1L;
    private Long otherCorrectId = 2L;
    private Long incorrectId = 0L;
    private String idWrongFormat = "wrongformat";

    private int expectedNumCourses = 0;

    private Course expectedCourse = new Course(correctId, "Integration Course", "Test course", new Teacher("TestTeacher"), BigDecimal.ZERO, 20);
    private Course otherCourse = new Course(otherCorrectId, "Integration Course", "Test course", new Teacher("TestTeacher"), BigDecimal.ZERO, 20);

    @Before
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        expectedNumCourses = repository.findAll().size() + 2;
        repository.save(expectedCourse);
        repository.save(otherCourse);
    }

    @Test
    public void get_existent_course() throws Exception {

        MvcResult result = mockMvc.perform(
                get("/api/courses/" + correctId))
                .andExpect(status().isOk())
                .andReturn();

        Course course = objectMapper.readValue(result.getResponse().getContentAsString(), Course.class);

        assertThat(course, equalTo(expectedCourse));
    }

    @Test
    public void get_non_existent_course() throws Exception {

        mockMvc.perform(
                get("/api/courses/" + incorrectId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void get_course_with_wrong_id_format() throws Exception {

        mockMvc.perform(
                get("/api/courses/" + idWrongFormat))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void get_all_courses() throws Exception {

        MvcResult result = mockMvc.perform(
                get("/api/courses"))
                .andExpect(status().isOk())
                .andReturn();

        JavaType type = objectMapper.getTypeFactory().
                constructCollectionType(Collection.class, Course.class);

        Collection<Course> courses = objectMapper.readValue(result.getResponse().getContentAsString(), type);

        assertThat("Wrong number of courses returned", courses.size(), equalTo(expectedNumCourses));
    }

    @Test
    public void tearDown() {
        repository.remove(expectedCourse);
        repository.remove(otherCourse);
    }
}
