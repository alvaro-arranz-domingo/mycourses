package com.lastminute.mycourses.infrastructure.entry.rest;

import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.primary.AddStudentToCourseRequest;

/**
 * Created by administrator on 3/12/15.
 */
public class AddStudentToCourseRestRequest implements AddStudentToCourseRequest {

    private Long courseId;
    private Student student;

    public AddStudentToCourseRestRequest(Long courseId, Student student) {
        this.courseId = courseId;
        this.student = student;
    }

    @Override
    public Long getCourseId() {
        return courseId;
    }

    @Override
    public Student getStudent() {
        return student;
    }
}
