package com.playground.payment_service.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private String status;
    private String message;
    private String provider;
    private String externalRef;
    private LocalDateTime createdAt;
}
