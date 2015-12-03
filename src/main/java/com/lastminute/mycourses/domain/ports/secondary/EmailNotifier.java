package com.lastminute.mycourses.domain.ports.secondary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;

public interface EmailNotifier {
    void studentEnrolled(Student student, Course course);
}
