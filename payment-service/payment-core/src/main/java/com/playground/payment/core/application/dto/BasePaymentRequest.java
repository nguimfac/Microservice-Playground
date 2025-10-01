package com.playground.payment.core.application.dto;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// Marker interface for payment requests, used for Jackson polymorphic deserialization
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "provider", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AbsaPaymentRequest.class, name = "absa"),
    @JsonSubTypes.Type(value = OrangeMoneyPaymentRequest.class, name = "om")
})
public interface BasePaymentRequest {
    String provider();
    String orderId();
    BigDecimal amount();
    String benificairyName();
}

