package com.playground.payment_service.adpaters.inbound;

import org.springframework.stereotype.Service;

import com.playground.payment_service.application.dto.AbsaPaymentRequest;
import com.playground.payment_service.application.dto.BasePaymentRequest;
import com.playground.payment_service.application.dto.PaymentResponse;
import com.playground.payment_service.domain.models.AbsaPaymentEntity;
import com.playground.payment_service.domain.ports.inbound.PaymentService;
import com.playground.payment_service.infrastructure.mapper.AbsaPaymentMapper;

import lombok.RequiredArgsConstructor;

@Service("absa")
@RequiredArgsConstructor
public class AbsaPaymentService implements PaymentService {

    private final AbsaPaymentMapper mapper;

    @Override
    public String authenticate(BasePaymentRequest request) {
        return "absa-token-sample";
    }

    @Override
    public PaymentResponse processPayment(BasePaymentRequest request, String authToken) {
        AbsaPaymentRequest absaReq = (AbsaPaymentRequest) request;
        AbsaPaymentEntity entity = mapper.toEntity(absaReq);
        return new PaymentResponse(
            entity.getId(),
            entity.getStatus(),
            "ABSA payment initiated",
            "ABSA",
            null,
            entity.getCreatedAt()
        );
    }

    @Override
    public PaymentResponse checkPayment(Long paymentId) {
        return new PaymentResponse(
            paymentId,
            "PENDING",
            "ABSA check not implemented yet",
            "ABSA",
            null,
            null
        );
    }
}
