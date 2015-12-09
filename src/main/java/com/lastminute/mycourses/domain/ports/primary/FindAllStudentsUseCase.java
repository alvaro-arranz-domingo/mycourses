package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.secondary.StudentRepository;

import java.util.Collection;

/**
 * Created by administrator on 7/12/15.
 */
public class FindAllStudentsUseCase {

    private StudentRepository repository;

    public FindAllStudentsUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public Collection<Student> execute() {
        return repository.findAll();
    }
}
