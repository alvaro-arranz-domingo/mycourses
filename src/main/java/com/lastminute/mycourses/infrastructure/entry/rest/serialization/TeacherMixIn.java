package com.lastminute.mycourses.infrastructure.entry.rest.serialization;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by administrator on 2/12/15.
 */
public class TeacherMixIn {

    public TeacherMixIn(@JsonProperty("name") String name){}
}
