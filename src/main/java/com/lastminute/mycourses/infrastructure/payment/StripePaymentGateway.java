package com.lastminute.mycourses.infrastructure.payment;

import com.lastminute.mycourses.domain.model.Course;
import com.lastminute.mycourses.domain.model.Student;
import com.lastminute.mycourses.domain.ports.secondary.PaymentGateway;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by administrator on 4/12/15.
 */
public class StripePaymentGateway implements PaymentGateway {

    private String stripeApiKey;

    public StripePaymentGateway(String stripeApiKey) {
        this.stripeApiKey = stripeApiKey;
    }

    @Override
    public boolean payCourse(Student student, Course course) {

        Stripe.apiKey = stripeApiKey;

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", course.getPrice());
        chargeParams.put("currency", "usd");
        Map<String, Object> sourceParams = new HashMap<String, Object>();
        sourceParams.put("number", student.getVisaCard().getCardNumber());
        sourceParams.put("exp_month", student.getVisaCard().getExpirationMonth().getMonth());
        sourceParams.put("exp_year", student.getVisaCard().getExpirationYear().getYear());
        sourceParams.put("cvc", student.getVisaCard().getCvc());
        chargeParams.put("source", sourceParams);
        chargeParams.put("description", "Charge for " + student.getEmailAddress());

        try {
            Charge.create(chargeParams);
        } catch (CardException e) {
            e.printStackTrace();
            return false;
        } catch (APIException | InvalidRequestException | APIConnectionException | AuthenticationException e) {
            e.printStackTrace();
            throw new StripeException("Charge failed", e);
        }

        return true;
    }
}
