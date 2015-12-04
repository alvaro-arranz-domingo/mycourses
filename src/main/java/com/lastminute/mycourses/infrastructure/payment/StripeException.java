package com.lastminute.mycourses.infrastructure.payment;

public class StripeException extends RuntimeException {

    public StripeException(String message, Exception cause) {
        super(message, cause);
    }
}
