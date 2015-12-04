package com.lastminute.mycourses.infrastructure.notifier.email;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.secondary.EmailNotifier;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by administrator on 3/12/15.
 */
public class SimpleEmailNotifier implements EmailNotifier {

    private MailSender mailSender;

    public SimpleEmailNotifier(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void studentEnrolled(Student student, Course course) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(student.emailAddress);
        msg.setText(createEmailMessage(student.getName(), course.getName()));
        msg.setSubject(createSubjectMessage(course.getName()));

        try{
            mailSender.send(msg);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private String createEmailMessage(String studentName, String courseName) {
        return "Dear " + studentName + ", thank you for your subscription to course " + courseName;
    }

    private String createSubjectMessage(String courseName) {
        return "You have enrolled to " + courseName;
    }
}
