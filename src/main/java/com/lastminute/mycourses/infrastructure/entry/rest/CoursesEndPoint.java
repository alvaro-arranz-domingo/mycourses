package com.lastminute.mycourses.infrastructure.entry.rest;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.ports.primary.AddCourseUseCase;
import com.lastminute.mycourses.domain.ports.primary.AddStudentUseCase;
import com.lastminute.mycourses.domain.ports.primary.FindAllCoursesUseCase;
import com.lastminute.mycourses.domain.ports.primary.FindCourseUseCase;
import com.lastminute.mycourses.infrastructure.entry.rest.exceptions.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by administrator on 1/12/15.
 */
@RestController
@RequestMapping("/api/courses")
public class CoursesEndPoint {

    private FindCourseUseCase findCourseUseCase;
    private FindAllCoursesUseCase findAllCoursesUseCase;
    private AddCourseUseCase addCourseUseCase;

    @Autowired
    public CoursesEndPoint(FindCourseUseCase useCase, FindAllCoursesUseCase findAllCoursesUseCase, AddCourseUseCase addCourseUseCase) {
        this.findCourseUseCase = useCase;
        this.findAllCoursesUseCase = findAllCoursesUseCase;
        this.addCourseUseCase = addCourseUseCase;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Course getCourse(@PathVariable Long id) throws CourseNotFoundException {

        Optional<Course> course = findCourseUseCase.execute(id);

        if (!course.isPresent()) {
            throw new CourseNotFoundException();
        }

        return course.get();
    }

    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Collection<Course> getCourses() {

        return findAllCoursesUseCase.execute();
    }

    @RequestMapping(path = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addCourse(@RequestBody Course course) {

        addCourseUseCase.execute(course);
    }
}
