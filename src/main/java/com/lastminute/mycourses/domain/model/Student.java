package com.lastminute.mycourses.domain.model;

/**
 * Created by administrator on 2/12/15.
 */
public class Student {

    public String name;
    public String emailAddress;
    public VisaCard visaCard;

    public Student(String name, String emailAddress, VisaCard visaCard) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.visaCard = visaCard;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }

    public VisaCard getVisaCard() {
        return visaCard;
    }
}
