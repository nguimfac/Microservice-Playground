package com.playground.payment_service.application.strategy;

import org.springframework.stereotype.Service;

import com.playground.payment_service.application.dto.AbsaPaymentRequest;
import com.playground.payment_service.application.dto.BasePaymentRequest;
import com.playground.payment_service.application.dto.PaymentResponse;
import com.playground.payment_service.application.mapper.AbsaPaymentMapper;
import com.playground.payment_service.domain.models.AbsaPaymentEntity;
import com.playground.payment_service.domain.ports.inbound.PaymentService;
import com.playground.payment_service.infrastructure.utils.PaymentProcessor;

import lombok.RequiredArgsConstructor;

@Service("absa")
@RequiredArgsConstructor
public class AbsaPaymentService extends PaymentProcessor implements PaymentService {

    private final AbsaPaymentMapper mapper;

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
        return "absa-token-sample";
    }

    @Override
    protected PaymentResponse processPayment(BasePaymentRequest request, String authToken) {
        AbsaPaymentRequest absaReq = (AbsaPaymentRequest) request;
        AbsaPaymentEntity entity = mapper.toEntity(absaReq);
        return PaymentResponse.builder()
                .paymentId(entity.getId())
                .status(entity.getStatus())
                .message("ABSA payment initiated")
                .provider("ABSA")
                .createdAt(entity.getCreatedAt())
                .build();
    }

    @Override
    protected PaymentResponse checkPayment(Long paymentId) {
        return PaymentResponse.builder()
                .paymentId(paymentId)
                .status("PENDING")
                .message("ABSA check not implemented yet")
                .provider("ABSA")
                .build();
    }
}
