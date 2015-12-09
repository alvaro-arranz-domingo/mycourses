package com.lastminute.mycourses.infrastructure.entry.rest;

import com.lastminute.mycourses.domain.model.Enrollment;
import com.lastminute.mycourses.domain.ports.primary.AddEnrollmentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by administrator on 7/12/15.
 */
@RestController
@RequestMapping(path = "/api/enrollments")
public class EnrollmentsEndPoint {

    private AddEnrollmentUseCase addEnrollmentUseCase;

    @Autowired
    public EnrollmentsEndPoint(AddEnrollmentUseCase addEnrollmentUseCase) {
        this.addEnrollmentUseCase = addEnrollmentUseCase;
    }

    @RequestMapping(path = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> save_enrollment(@RequestBody Enrollment enrollment) {

        AddEnrollmentRestResponse restResponse = new AddEnrollmentRestResponse();

        addEnrollmentUseCase.execute(enrollment, restResponse);

        return restResponse.getStatus();
    }

}
