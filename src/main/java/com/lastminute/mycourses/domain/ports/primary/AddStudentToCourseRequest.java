package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Student;

/**
 * Created by administrator on 3/12/15.
 */
public interface AddStudentToCourseRequest {

    Long getCourseId();

    Student getStudent();
}
