package com.lastminute.mycourses.domain.model;

import static java.lang.String.format;

/**
 * Created by administrator on 3/12/15.
 */
public class VisaCard {

    private Long cardNumber;
    private Month expirationMonth;
    private Year expirationYear;
    private Integer cvc;

    public VisaCard(Long cardNumber, Month expirationMonth, Year expirationYear, Integer cvc) {
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvc = cvc;
    }

    private void requireBetween(Integer n, int lowerBound, int upperBound) {
        if (n < lowerBound || n > upperBound) {
            throw new IllegalArgumentException(format("%d is not between %d and %d", n, lowerBound, upperBound));
        }
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public Month getExpirationMonth() {
        return expirationMonth;
    }

    public Year getExpirationYear() {
        return expirationYear;
    }

    public Integer getCvc() {
        return cvc;
    }
}
