package com.lastminute.mycourses.infrastructure.entry.rest.serialization;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by administrator on 7/12/15.
 */
public class EnrollmentMixIn {

    public EnrollmentMixIn(@JsonProperty("id") Long id, @JsonProperty("courseId") Long courseId, @JsonProperty("studentId") Long studentId) {}
}
