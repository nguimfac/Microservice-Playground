package com.playground.payment_service.domain.ports.outbound;

import java.util.List;

import com.playground.payment_service.domain.models.OrangeMoneyEntity;

public interface OrangeMoneyPaymentRepository {
    OrangeMoneyEntity save(OrangeMoneyEntity entity);

    OrangeMoneyEntity findById(Long id);

    List<OrangeMoneyEntity> findAll();

    OrangeMoneyEntity update(Long id, OrangeMoneyEntity entity);

    boolean deleteById(Long id);
}
