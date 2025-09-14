package com.playground.payment_service.infrastructure.factories;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.playground.payment_service.domain.ports.inbound.PaymentService;
import com.playground.payment_service.infrastructure.exceptions.UnknownProviderException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentFactory {

    private final Map<String, PaymentService> services;

    public PaymentService getFactory(String provider) {
        PaymentService svc = services.get(provider);
        if (svc == null) {
            throw new UnknownProviderException(provider);
        }
        return svc;
    }
}
