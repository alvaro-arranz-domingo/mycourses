package com.lastminute.mycourses.domain.model;

/**
 * Created by administrator on 3/12/15.
 */
public class VisaCard {

    private Long cardNumber;
    private Integer expirationMonth;
    private Integer expirationYear;
    private Integer cvc;

    public VisaCard(Long cardNumber, Integer expirationMonth, Integer expirationYear, Integer cvc) {
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvc = cvc;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    public Integer getExpirationYear() {
        return expirationYear;
    }

    public Integer getCvc() {
        return cvc;
    }
}
