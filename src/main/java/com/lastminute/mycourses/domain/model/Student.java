package com.lastminute.mycourses.domain.model;

/**
 * Created by administrator on 2/12/15.
 */
public class Student {

    public String name;
    public String emailAddress;

    public Student(String name, String emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }

}
