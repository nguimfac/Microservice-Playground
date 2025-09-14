package com.playground.payment_service.application.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonTypeName("om")
public record OrangeMoneyPaymentRequest(
    @NotBlank(message = "Provider is mandatory") String provider,
    @NotBlank(message = "Order ID is mandatory") String orderId,
    @NotNull(message = "Amount is mandatory") @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero") BigDecimal amount,
    @NotBlank(message = "Benificiary name is mandatory") String benificairyName,
    @NotBlank(message = "Phone number is mandatory") String phoneNumber,
    @Email(message = "Email address must be valid") @NotBlank(message = "Email address is mandatory") String emailAddress
) implements BasePaymentRequest {}
