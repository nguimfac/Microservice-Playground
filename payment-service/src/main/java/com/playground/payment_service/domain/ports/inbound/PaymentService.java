package com.playground.payment_service.domain.ports.inbound;

import com.playground.payment_service.application.dto.BasePaymentRequest;
import com.playground.payment_service.application.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse pay(BasePaymentRequest request);

    PaymentResponse check(Long paymentId);
}
