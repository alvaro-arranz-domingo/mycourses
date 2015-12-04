package com.lastminute.mycourses.infrastructure.entry.rest.serialization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lastminute.mycourses.domain.model.Month;
import com.lastminute.mycourses.domain.model.Year;

/**
 * Created by administrator on 3/12/15.
 */
public class VisaCardMixin {

    public VisaCardMixin(@JsonProperty("cardNumber") Long cardNumber, @JsonProperty("expirationMonth") Month expirationMonth,
                         @JsonProperty("expirationYear") Year expirationYear, @JsonProperty("cvc") Integer cvc) {}
}
