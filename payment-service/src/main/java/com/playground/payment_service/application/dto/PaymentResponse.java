package com.playground.payment_service.application.dto;

import java.time.LocalDateTime;

public record PaymentResponse(
    Long paymentId,
    String status,
    String message,
    String provider,
    String externalRef,
    LocalDateTime createdAt
) {}
