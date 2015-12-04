package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;

/**
 * Created by administrator on 3/12/15.
 */
public interface AddStudentToCourseResponse {

    void courseFull();

    void courseNotFound();

    void paymentFailed();

    void enrolled(Course course);
}
