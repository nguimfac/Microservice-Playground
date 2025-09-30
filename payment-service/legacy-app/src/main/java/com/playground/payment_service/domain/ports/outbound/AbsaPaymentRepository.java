package com.playground.payment_service.domain.ports.outbound;

import java.util.List;

import com.playground.payment_service.domain.models.AbsaPaymentEntity;

public interface AbsaPaymentRepository {
    AbsaPaymentEntity save(AbsaPaymentEntity entity);

    AbsaPaymentEntity findById(Long id);

    List<AbsaPaymentEntity> findAll();

    AbsaPaymentEntity update(Long id, AbsaPaymentEntity entity);

    boolean deleteById(Long id);
}
