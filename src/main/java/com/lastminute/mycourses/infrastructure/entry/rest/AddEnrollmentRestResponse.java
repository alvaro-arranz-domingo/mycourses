package com.lastminute.mycourses.infrastructure.entry.rest;

import com.lastminute.mycourses.domain.model.Enrollment;
import com.lastminute.mycourses.domain.ports.primary.AddEnrollmentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by administrator on 3/12/15.
 */
public class AddEnrollmentRestResponse implements AddEnrollmentResponse {

    private ResponseEntity<Void> entity;

    public ResponseEntity<Void> getStatus() {
        return entity;
    }

    @Override
    public void courseFull() {
        entity = new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @Override
    public void courseNotFound() {
        entity = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @Override
    public void studentNotFound() {
        entity = new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @Override
    public void paymentFailed() {
        entity = new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }

    @Override
    public void enrolled(Enrollment enrollment) {
        entity = new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
