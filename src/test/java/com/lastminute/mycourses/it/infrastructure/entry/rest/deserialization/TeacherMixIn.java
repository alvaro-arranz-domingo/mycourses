package com.lastminute.mycourses.it.infrastructure.entry.rest.deserialization;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by administrator on 2/12/15.
 */
public class TeacherMixIn {

    public TeacherMixIn(@JsonProperty("name") String name){}
}
