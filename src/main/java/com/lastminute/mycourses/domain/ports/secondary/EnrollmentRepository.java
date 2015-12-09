package com.lastminute.mycourses.domain.ports.secondary;

import com.lastminute.mycourses.domain.model.Enrollment;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by administrator on 7/12/15.
 */
public interface EnrollmentRepository {

    Optional<Enrollment> findById(Long id);

    Collection<Enrollment> findByStudent(Long idStudent);

    Collection<Enrollment> findByCourse(Long idCourse);

    Optional<Enrollment> findByCourseAndStudent(Long idCourse, Long idStudent);

    void save(Enrollment enrollment);

    void remove(Enrollment enrollment);
}
