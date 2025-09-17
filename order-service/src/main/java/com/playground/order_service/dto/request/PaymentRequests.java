package com.playground.order_service.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentRequests {

    public record AbsaPaymentRequest(
            @NotBlank String provider,
            @NotBlank String orderId,
            @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount,
            @NotBlank String benificairyName,
            @NotBlank String benificairyAccountNumber,
            @NotBlank String beneficiaryBankName
    ) {}

    public record OrangeMoneyPaymentRequest(
            @NotBlank String provider,
            @NotBlank String orderId,
            @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal amount,
            @NotBlank String benificairyName,
            @NotBlank String phoneNumber,
            @Email @NotBlank String emailAddress
    ) {}
}
