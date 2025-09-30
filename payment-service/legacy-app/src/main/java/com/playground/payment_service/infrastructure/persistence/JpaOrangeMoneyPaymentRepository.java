package com.playground.payment_service.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playground.payment_service.domain.models.OrangeMoneyEntity;

@Repository
public interface JpaOrangeMoneyPaymentRepository extends JpaRepository<OrangeMoneyEntity, Long> {
}
