package com.lastminute.mycourses.domain.ports.secondary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;

/**
 * Created by administrator on 3/12/15.
 */
public interface PaymentGateway {

    boolean payCourse(Student student, Course course);
}
