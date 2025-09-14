package com.playground.payment_service.application.strategy;

import org.springframework.stereotype.Service;

import com.playground.payment_service.application.dto.BasePaymentRequest;
import com.playground.payment_service.application.dto.OrangeMoneyPaymentRequest;
import com.playground.payment_service.application.dto.PaymentResponse;
import com.playground.payment_service.application.mapper.OrangeMoneyPaymentMapper;
import com.playground.payment_service.domain.models.OrangeMoneyEntity;
import com.playground.payment_service.domain.ports.inbound.PaymentService;
import com.playground.payment_service.infrastructure.utils.PaymentProcessor;

import lombok.RequiredArgsConstructor;

@Service("om")
@RequiredArgsConstructor
public class OrangePaymentService extends PaymentProcessor implements PaymentService {

    private final OrangeMoneyPaymentMapper mapper;

    @Override
    public PaymentResponse pay(BasePaymentRequest request) {
        return handlePayment(request);
    }

    @Override
    public PaymentResponse check(Long paymentId) {
        return checkPayment(paymentId);
    }

    @Override
    protected String authenticate(BasePaymentRequest request) {
        return "orange-token-sample";
    }

    @Override
    protected PaymentResponse processPayment(BasePaymentRequest request, String authToken) {
        OrangeMoneyPaymentRequest omReq = (OrangeMoneyPaymentRequest) request;
        OrangeMoneyEntity entity = mapper.toEntity(omReq);
        // repository.save(entity); // décommentez si un repository est disponible
        return PaymentResponse.builder()
                .paymentId(entity.getId())
                .status(entity.getStatus())
                .message("Orange Money payment initiated")
                .provider("ORANGE_MONEY")
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    protected PaymentResponse checkPayment(Long paymentId) {
    // Rechercher en base et/ou interroger le provider si nécessaire
        return PaymentResponse.builder()
                .paymentId(paymentId)
                .status("PENDING")
                .message("Orange Money check not implemented yet")
                .provider("ORANGE_MONEY")
                .build();
    }
}
