package com.lastminute.mycourses.infrastructure.repository;

import com.lastminute.mycourses.domain.model.Enrollment;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

/**
 * Created by administrator on 7/12/15.
 */
public class VolatileMapEnrollmentRepositoryTest {

    private VolatileMapEnrollmentRepository repository = new VolatileMapEnrollmentRepository();

    private Long courseId = 0L;
    private Long studentId = 0L;

    private Long enrollmentId = 0L;
    private Enrollment expectedEnrollment = new Enrollment(enrollmentId, courseId, studentId);

    private Long studentId1 = 0L;
    private Long studentId2 = 1L;
    private Long studentId3 = 2L;

    private Enrollment enrollmentCorrectCourse1 = new Enrollment(1L, courseId, studentId1);
    private Enrollment enrollmentCorrectCourse2 = new Enrollment(2L, courseId, studentId2);
    private Enrollment enrollmentCorrectCourse3 = new Enrollment(3L, courseId, studentId3);

    @Test
    public void save_enrollment_into_repository_and_retrieve() {

        repository.save(expectedEnrollment);
        Optional<Enrollment> enrollment = repository.findById(enrollmentId);

        assertThat(enrollment.get(), equalTo(expectedEnrollment));
    }

    @Test
    public void remove_enrollment() {

        repository.save(expectedEnrollment);

        repository.remove(expectedEnrollment);
        Optional<Enrollment> enrollment = repository.findById(enrollmentId);

        assertThat(enrollment, equalTo(Optional.empty()));
    }

    @Test
    public void find_by_course_id_with_various_elements() {

        repository.save(enrollmentCorrectCourse1);
        repository.save(enrollmentCorrectCourse2);
        repository.save(enrollmentCorrectCourse3);

        Collection<Enrollment> enrollments = repository.findByCourse(courseId);

        Collection<Long> studentIds = enrollments.stream().map(x -> x.getStudentId()).collect(Collectors.toCollection(ArrayList::new));

        assertTrue("The returned number of students is wrong", enrollments.size() == 3);
        assertTrue("The enrollments does not contain student1", studentIds.contains(studentId1));
        assertTrue("The enrollments does not contain student2", studentIds.contains(studentId2));
        assertTrue("The enrollments does not contain student3", studentIds.contains(studentId3));
    }
}