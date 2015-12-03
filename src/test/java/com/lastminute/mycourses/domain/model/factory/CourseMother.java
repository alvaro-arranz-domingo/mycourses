package com.lastminute.mycourses.domain.model.factory;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Teacher;

import java.math.BigDecimal;

/**
 * Created by administrator on 3/12/15.
 */
public class CourseMother {

    static public Course createCorrectTestCourse(Long id) {
        return new Course(id, "TDD", "TDD cycle. Mocks and stubs.", new Teacher("Teacher name"), BigDecimal.ONE, 20);
    }

    static public Course createCorrectTestCourseWithCapacity(Long id, Integer capacity) {
        return new Course(id, "TDD", "TDD cycle. Mocks and stubs.", new Teacher("Teacher name"), BigDecimal.ONE, capacity);
    }
}
