package com.lastminute.mycourses.infrastructure.repository;

import com.lastminute.mycourses.domain.model.Enrollment;
import com.lastminute.mycourses.domain.ports.secondary.EnrollmentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by administrator on 7/12/15.
 */
public class VolatileMapEnrollmentRepository extends VolatileMapRepository<Enrollment> implements EnrollmentRepository {

    @Override
    public Collection<Enrollment> findByStudent(Long idStudent) {
        return map.values().stream().filter(x -> x.getStudentId() == idStudent).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Collection<Enrollment> findByCourse(Long idCourse) {
        return map.values().stream().filter(x -> x.getCourseId() == idCourse).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Optional<Enrollment> findByCourseAndStudent(Long idCourse, Long idStudent) {
        Collection<Enrollment> enrollments = map.values().stream().filter(x -> x.getStudentId() == idStudent && x.getCourseId() == idCourse)
                .collect(Collectors.toCollection(ArrayList::new));

        if (enrollments.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(enrollments.iterator().next());
    }

    @Override
    public void save(Enrollment enrollment) {
        save(enrollment.getId(), enrollment);
    }

    @Override
    public void remove(Enrollment enrollment) {
        remove(enrollment.getId());
    }
}
