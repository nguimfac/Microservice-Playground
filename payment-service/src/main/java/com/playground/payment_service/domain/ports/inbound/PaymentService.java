package com.playground.payment_service.domain.ports.inbound;

import com.playground.payment_service.application.dto.BasePaymentRequest;
import com.playground.payment_service.application.dto.PaymentResponse;

public interface PaymentService {
    
    String authenticate(BasePaymentRequest request);

    PaymentResponse processPayment(BasePaymentRequest request, String authToken);

    PaymentResponse checkPayment(Long paymentId);

    default PaymentResponse handlePayment(BasePaymentRequest request) {
        String authToken = authenticate(request);
        return processPayment(request, authToken);
    }
}
