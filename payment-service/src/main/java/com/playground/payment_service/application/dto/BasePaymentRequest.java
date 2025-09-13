package com.playground.payment_service.application.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public abstract class BasePaymentRequest {

    @NotBlank(message = "Order ID is mandatory")
    private String orderId;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Benificiary name is mandatory")
    private String benificairyName;
}
