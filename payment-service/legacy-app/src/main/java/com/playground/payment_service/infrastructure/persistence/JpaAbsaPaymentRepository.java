package com.playground.payment_service.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playground.payment_service.domain.models.AbsaPaymentEntity;

@Repository
public interface JpaAbsaPaymentRepository extends JpaRepository<AbsaPaymentEntity, Long> {
}
