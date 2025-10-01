package com.playground.payment_db.port.outbound;

import java.util.List;

import com.playground.payment_db.infrastructure.persistence.entities.AbsaPaymentEntity;

public interface AbsaPaymentRepository {
    AbsaPaymentEntity save(AbsaPaymentEntity entity);

    AbsaPaymentEntity findById(Long id);

    List<AbsaPaymentEntity> findAll();

    AbsaPaymentEntity update(Long id, AbsaPaymentEntity entity);

    boolean deleteById(Long id);
}
