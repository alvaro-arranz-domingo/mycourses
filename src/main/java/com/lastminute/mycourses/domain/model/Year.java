package com.lastminute.mycourses.domain.model;

import java.util.IllegalFormatException;

import static java.lang.String.*;

/**
 * Created by administrator on 4/12/15.
 */
public class Year {

    private Integer year;

    public Year(Integer year) {
        if (year < 1900 || year > 2100) {
            throw  new IllegalArgumentException(format("Year should be between %d and %d", 1900, 2100));
        }

        this.year = year;
    }

    public Integer getYear() {
        return year;
    }
}
