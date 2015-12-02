package com.lastminute.mycourses.infrastructure.entry.rest.serialization;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by administrator on 2/12/15.
 */
public class StudentMixIn {

    StudentMixIn(@JsonProperty("name") String name, @JsonProperty("emailAddress") String emailAddress) {}
}
