package com.lastminute.mycourses.domain.ports.secondary;

import com.lastminute.mycourses.domain.model.Student;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by administrator on 4/12/15.
 */
public interface StudentRepository {

    Optional<Student> findById(Long id);

    Collection<Student> findAll();

    void save(Student student);
}
