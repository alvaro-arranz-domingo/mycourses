package com.lastminute.mycourses.it.infrastructure.entry.rest.deserialization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lastminute.mycourses.domain.model.Teacher;

import java.math.BigDecimal;

/**
 * Created by administrator on 2/12/15.
 */
public abstract class CourseMixIn {

    CourseMixIn(@JsonProperty("id") Long id, @JsonProperty("name") String name,
                @JsonProperty("description") String description, @JsonProperty("teacher") Teacher teacher,
                @JsonProperty("price")BigDecimal price){}
}
