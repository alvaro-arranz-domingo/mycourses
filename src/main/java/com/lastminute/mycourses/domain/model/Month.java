package com.lastminute.mycourses.domain.model;

import static java.lang.String.format;

/**
 * Created by administrator on 3/12/15.
 */
public class Month {

    private Integer month;

    public Month(Integer month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(format("Month %d is not between %d and %d", month, 1, 12));
        }
        
        this.month = month;
    }

    public Integer getMonth() {
        return month;
    }
}
