package com.lastminute.mycourses.infrastructure.entry.rest;

import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.primary.AddStudentUseCase;
import com.lastminute.mycourses.domain.ports.primary.FindAllStudentsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Created by administrator on 2/12/15.
 */
@RestController
@RequestMapping("/api/students")
public class StudentsEndPoint {

    private AddStudentUseCase addStudentUseCase;
    private FindAllStudentsUseCase findAllStudentsUseCase;

    @Autowired
    public StudentsEndPoint(AddStudentUseCase addStudentUseCase, FindAllStudentsUseCase findAllStudentsUseCase) {
        this.addStudentUseCase = addStudentUseCase;
        this.findAllStudentsUseCase = findAllStudentsUseCase;
    }

    @RequestMapping(path = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addStudent(@RequestBody Student student) {
        addStudentUseCase.execute(student);
    }

    @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Collection<Student> getStudents() {
        return findAllStudentsUseCase.execute();
    }
}
