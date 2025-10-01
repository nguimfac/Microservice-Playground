package com.playground.payment_api.infrastructure.factories;

import com.playground.payment_api.domain.ports.inbound.PaymentService;

public interface PaymentFactory {
    PaymentService getStrategy(String provider);
}
