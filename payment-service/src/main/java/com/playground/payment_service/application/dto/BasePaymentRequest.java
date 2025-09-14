package com.playground.payment_service.application.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "provider", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AbsaPaymentRequest.class, name = "absa"),
    @JsonSubTypes.Type(value = OrangeMoneyPaymentRequest.class, name = "om")
})
public abstract class BasePaymentRequest {

    @NotBlank(message = "Provider is mandatory")
    private String provider;

    @NotBlank(message = "Order ID is mandatory")
    private String orderId;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Benificiary name is mandatory")
    private String benificairyName;
}
