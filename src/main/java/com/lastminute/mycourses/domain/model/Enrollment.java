package com.lastminute.mycourses.domain.model;

import java.util.Objects;

/**
 * Created by administrator on 7/12/15.
 */
public class Enrollment {

    private Long id;
    private Long courseId;

    private Long studentId;

    public Enrollment(Long id, Long courseId, Long studentId) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(courseId, that.courseId) &&
                Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseId, studentId);
    }
}
