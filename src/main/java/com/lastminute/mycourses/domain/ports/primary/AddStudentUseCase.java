package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.secondary.StudentRepository;

/**
 * Created by administrator on 7/12/15.
 */
public class AddStudentUseCase {

    private StudentRepository repository;

    public AddStudentUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public void execute(Student student) {
        repository.save(student);
    }
}
