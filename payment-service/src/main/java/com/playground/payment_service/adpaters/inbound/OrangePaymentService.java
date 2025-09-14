package com.playground.payment_service.adpaters.inbound;

import org.springframework.stereotype.Service;

import com.playground.payment_service.application.dto.BasePaymentRequest;
import com.playground.payment_service.application.dto.OrangeMoneyPaymentRequest;
import com.playground.payment_service.application.dto.PaymentResponse;
import com.playground.payment_service.domain.models.OrangeMoneyEntity;
import com.playground.payment_service.domain.ports.inbound.PaymentService;
import com.playground.payment_service.infrastructure.mapper.OrangeMoneyPaymentMapper;
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
        return new PaymentResponse(
            entity.getId(),
            entity.getStatus(),
            "Orange Money payment initiated",
            "ORANGE_MONEY",
            null,
            entity.getCreatedAt()
        );
    }

    @Override
    protected PaymentResponse checkPayment(Long paymentId) {
        return new PaymentResponse(
            paymentId,
            "PENDING",
            "Orange Money check not implemented yet",
            "ORANGE_MONEY",
            null,
            null
        );
    }
}
