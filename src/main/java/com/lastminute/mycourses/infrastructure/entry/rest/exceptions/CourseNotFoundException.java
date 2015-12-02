package com.lastminute.mycourses.infrastructure.entry.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by administrator on 1/12/15.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends Exception {
}
