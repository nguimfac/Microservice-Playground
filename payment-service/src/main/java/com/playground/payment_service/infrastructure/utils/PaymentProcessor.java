package com.playground.payment_service.infrastructure.utils;

import com.playground.payment_service.application.dto.BasePaymentRequest;
import com.playground.payment_service.application.dto.PaymentResponse;

public abstract class PaymentProcessor {
    
    public final PaymentResponse handlePayment(BasePaymentRequest request) {
        String authToken = authenticate(request);
        return processPayment(request, authToken);
    }

    protected abstract String authenticate(BasePaymentRequest request);
    protected abstract PaymentResponse processPayment(BasePaymentRequest request, String authToken);
    protected abstract PaymentResponse checkPayment(Long paymentId);
}
