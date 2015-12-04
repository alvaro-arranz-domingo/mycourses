package com.lastminute.mycourses.infrastructure.entry.rest.serialization;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by administrator on 4/12/15.
 */
public class MonthMixIn {

    public MonthMixIn(@JsonProperty("month") Integer month){}
}
