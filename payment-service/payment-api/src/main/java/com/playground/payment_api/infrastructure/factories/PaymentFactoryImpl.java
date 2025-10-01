package com.playground.payment_api.infrastructure.factories;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.playground.payment.core.exceptions.UnknownProviderException;
import com.playground.payment_api.domain.ports.inbound.PaymentService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentFactoryImpl implements PaymentFactory{

    private final Map<String, PaymentService> services;

    @Override
    public PaymentService getStrategy(String provider) {
        PaymentService svc = services.get(provider);
        if (svc == null) {
            throw new UnknownProviderException(provider);
        }
        return svc;
    }
}
