package com.lastminute.mycourses.infrastructure.entry.rest;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.primary.AddStudentToCourseRequest;
import com.lastminute.mycourses.domain.ports.primary.AddStudentToCourseResponse;
import com.lastminute.mycourses.domain.ports.primary.AddStudentToCourseUseCase;
import com.lastminute.mycourses.infrastructure.entry.rest.exceptions.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by administrator on 2/12/15.
 */
@RestController
@RequestMapping("/api/courses/{courseId}/students")
public class StudentsEndPoint {

    private AddStudentToCourseUseCase addStudentToCourseUseCase;

    @Autowired
    public StudentsEndPoint(AddStudentToCourseUseCase addStudentToCourseUseCase) {
        this.addStudentToCourseUseCase = addStudentToCourseUseCase;
    }

    @RequestMapping(path = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addStudent(@PathVariable Long courseId, @RequestBody Student student) {

        AddStudentToCourseRestRequest request = new AddStudentToCourseRestRequest(courseId, student);
        AddStudentToCourseRestResponse response = new AddStudentToCourseRestResponse();

        addStudentToCourseUseCase.execute(request, response);

        return response.getStatus();
    }
}
