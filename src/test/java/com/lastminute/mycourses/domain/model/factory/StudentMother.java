package com.lastminute.mycourses.domain.model.factory;

import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.VisaCard;

/**
 * Created by administrator on 3/12/15.
 */
public class StudentMother {

    static public Student createCorrectTestStudent() {
        VisaCard visaCard = new VisaCard(543635L, 11, 15, 543);
        return new Student("nameTest", "emailTest@gmail.com", visaCard);
    }
}
