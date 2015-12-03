package com.lastminute.mycourses.infrastructure.entry.rest.serialization;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by administrator on 3/12/15.
 */
public class VisaCardMixin {

    public VisaCardMixin(@JsonProperty("cardNumber") Long cardNumber, @JsonProperty("expirationMonth") Integer expirationMonth,
                         @JsonProperty("expirationYear") Integer expirationYear, @JsonProperty("cvc") Integer cvc) {}
}
