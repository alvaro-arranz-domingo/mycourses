package com.lastminute.mycourses.domain.model.factory;

import com.lastminute.mycourses.domain.model.Teacher;

/**
 * Created by administrator on 3/12/15.
 */
public class TeacherMother {

    static public Teacher createCorrectTestTeacher() {
        return new Teacher("TestTeacher");
    }
}