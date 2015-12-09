package com.lastminute.mycourses.infrastructure.repository;

import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.secondary.StudentRepository;

/**
 * Created by administrator on 7/12/15.
 */
public class VolatileMapStudentRepository extends VolatileMapRepository<Student> implements StudentRepository {

    public void save(Student student) {
        save(student.getId(), student);
    }

    public void remove(Student student) {
        remove(student.getId());
    }
}
