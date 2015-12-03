package com.lastminute.mycourses.infrastructure.entry.rest;

import com.lastminute.mycourses.domain.model.Course;
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

    @Autowired
    public CoursesEndPoint(FindCourseUseCase useCase, FindAllCoursesUseCase findAllCoursesUseCase) {
        this.findCourseUseCase = useCase;
        this.findAllCoursesUseCase = findAllCoursesUseCase;
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
}
