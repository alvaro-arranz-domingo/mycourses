package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Enrollment;

/**
 * Created by administrator on 3/12/15.
 */
public interface AddEnrollmentResponse {

    void courseFull();

    void courseNotFound();

    void studentNotFound();

    void paymentFailed();

    void enrolled(Enrollment enrollment);
}
