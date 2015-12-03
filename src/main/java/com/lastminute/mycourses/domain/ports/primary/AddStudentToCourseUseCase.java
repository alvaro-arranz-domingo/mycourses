package com.lastminute.mycourses.domain.ports.primary;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.secondary.CourseRepository;
import com.lastminute.mycourses.domain.ports.secondary.EmailNotifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import java.util.Optional;

/**
 * Created by administrator on 2/12/15.
 */
public class AddStudentToCourseUseCase {

    private CourseRepository repository;
    private EmailNotifier emailNotifier;

    public AddStudentToCourseUseCase(CourseRepository repository, EmailNotifier emailNotifier) {
        this.repository = repository;
        this.emailNotifier = emailNotifier;
    }

    public Optional<Course> execute(Long courseId, Student student) {

        Optional<Course> course = repository.findCourseById(courseId);

        if (!course.isPresent()) {
            return Optional.empty();
        }

        course.get().addStudent(student);
        emailNotifier.studentEnrolled(student, course.get());
        return course;
    }

    private void sendNotificationEmail(String to, String subject, String message) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setText(message);
        msg.setSubject(subject);

        try{
            //mailSender.send(msg);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private String createEmailMessage(String studentName, String courseName) {
        return "Dear " + studentName + ", thank you for your subscription to course " + courseName;
    }
}
