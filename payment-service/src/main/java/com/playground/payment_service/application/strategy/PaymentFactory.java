package com.playground.payment_service.application.strategy;

import java.util.Map;

import org.springframework.stereotype.Component;
import com.playground.payment_service.application.exceptions.UnknownProviderException;

import com.playground.payment_service.domain.ports.inbound.PaymentService;

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
