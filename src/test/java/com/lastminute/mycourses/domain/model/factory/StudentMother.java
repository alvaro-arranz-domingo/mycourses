package com.lastminute.mycourses.domain.model.factory;

import com.lastminute.mycourses.domain.model.Month;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.model.VisaCard;
import com.lastminute.mycourses.domain.model.Year;

/**
 * Created by administrator on 3/12/15.
 */
public class StudentMother {

    static public Student createCorrectTestStudent(Long id) {
        VisaCard visaCard = new VisaCard(543635L, new Month(11), new Year(2010), 543);
        return new Student(id, "nameTest", "emailTest@gmail.com", visaCard);
    }

    static public Student createCorrectTestStudentForPayment(Long id) {
        VisaCard visaCard = new VisaCard(4242424242424242L, new Month(12), new Year(2016), 314);
        return new Student(id, "studentForPaymentTest", "emailTest@gmail.com", visaCard);
    }
}