package com.lastminute.mycourses.infrastructure.entry.rest;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.ports.primary.FindCourseUseCase;
import com.lastminute.mycourses.infrastructure.entry.rest.exceptions.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by administrator on 1/12/15.
 */
@RestController
@RequestMapping("/api/courses")
public class CoursesEndPoint {

    private FindCourseUseCase findCourseUseCase;

    @Autowired
    public CoursesEndPoint(FindCourseUseCase useCase) {
        this.findCourseUseCase = useCase;
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
}
