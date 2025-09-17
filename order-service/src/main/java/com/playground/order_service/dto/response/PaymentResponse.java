package com.playground.order_service.dto.response;

import java.time.LocalDateTime;

public record PaymentResponse(
        Long paymentId,
        String status,
        String message,
        String provider,
        String externalRef,
        LocalDateTime createdAt
) {}
