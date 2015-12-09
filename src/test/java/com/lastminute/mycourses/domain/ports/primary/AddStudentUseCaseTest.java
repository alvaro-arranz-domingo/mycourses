package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.factory.StudentMother;
import com.lastminute.mycourses.domain.ports.secondary.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by administrator on 7/12/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddStudentUseCaseTest {

    private AddStudentUseCase useCase;

    @Mock private StudentRepository repository;

    private Student student = StudentMother.createCorrectTestStudent(0L);

    @Before
    public void setUp() {
        useCase = new AddStudentUseCase(repository);
    }

    @Test
    public void add_correct_student() {

        useCase.execute(student);

        verify(repository).save(student);
    }

}