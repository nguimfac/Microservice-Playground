package com.playground.payment_api.adapters.inbound;

import org.springframework.stereotype.Service;

import com.playground.payment.core.application.dto.AbsaPaymentRequest;
import com.playground.payment.core.application.dto.BasePaymentRequest;
import com.playground.payment.core.application.dto.PaymentResponse;
import com.playground.payment_api.domain.ports.inbound.PaymentService;
import com.playground.payment_api.infrastructure.mappers.AbsaPaymentMapper;
import com.playground.payment_db.infrastructure.persistence.entities.AbsaPaymentEntity;
import com.playground.payment_db.port.outbound.AbsaPaymentRepository;

import lombok.RequiredArgsConstructor;

@Service("absa")
@RequiredArgsConstructor
public class AbsaPaymentService implements PaymentService {

    private final AbsaPaymentMapper mapper;
    private final AbsaPaymentRepository repository;

    @Override
    public String authenticate(BasePaymentRequest request) {
        return "absa-token-sample";
    }

    @Override
    public PaymentResponse processPayment(BasePaymentRequest request, String authToken) {
        AbsaPaymentRequest absaReq = (AbsaPaymentRequest) request;
        AbsaPaymentEntity entity = repository.save(mapper.toEntity(absaReq));
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
        AbsaPaymentEntity entity = repository.findById(paymentId);
        return new PaymentResponse(
            entity.getId(),
            entity.getStatus(),
            "ABSA payment initiated",
            "ABSA",
            null,
            entity.getCreatedAt()
        );
    }
}
