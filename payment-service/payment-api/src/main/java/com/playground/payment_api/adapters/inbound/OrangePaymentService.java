package com.playground.payment_api.adapters.inbound;

import org.springframework.stereotype.Service;

import com.playground.payment.core.application.dto.BasePaymentRequest;
import com.playground.payment.core.application.dto.OrangeMoneyPaymentRequest;
import com.playground.payment.core.application.dto.PaymentResponse;
import com.playground.payment_api.domain.ports.inbound.PaymentService;
import com.playground.payment_api.infrastructure.mappers.OrangeMoneyPaymentMapper;
import com.playground.payment_db.infrastructure.persistence.entities.OrangeMoneyEntity;
import com.playground.payment_db.port.outbound.OrangeMoneyPaymentRepository;

import lombok.RequiredArgsConstructor;

@Service("om")
@RequiredArgsConstructor
public class OrangePaymentService implements PaymentService {

    private final OrangeMoneyPaymentMapper mapper;
    private final OrangeMoneyPaymentRepository repository;

    @Override
    public String authenticate(BasePaymentRequest request) {
        return "orange-token-sample";
    }

    @Override
    public PaymentResponse processPayment(BasePaymentRequest request, String authToken) {
        OrangeMoneyPaymentRequest omReq = (OrangeMoneyPaymentRequest) request;
        OrangeMoneyEntity entity = repository.save(mapper.toEntity(omReq));
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
    public PaymentResponse checkPayment(Long paymentId) {
        OrangeMoneyEntity entity = repository.findById(paymentId);
        return new PaymentResponse(
            entity.getId(),
            entity.getStatus(),
            "Orange Money payment retrieved",
            "ORANGE_MONEY",
            null,
            entity.getCreatedAt()
        );
    }
}