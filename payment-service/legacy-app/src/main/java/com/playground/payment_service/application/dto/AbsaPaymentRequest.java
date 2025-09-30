package com.playground.payment_service.application.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonTypeName("absa")
public record AbsaPaymentRequest(
    @NotBlank(message = "Provider is mandatory") String provider,
    @NotBlank(message = "Order ID is mandatory") String orderId,
    @NotNull(message = "Amount is mandatory") @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero") BigDecimal amount,
    @NotBlank(message = "Benificiary name is mandatory") String benificairyName,
    @NotBlank(message = "Benificiary account number is mandatory") String benificairyAccountNumber,
    @NotBlank(message = "Beneficiary bank name is mandatory") String beneficiaryBankName
) implements BasePaymentRequest {}
