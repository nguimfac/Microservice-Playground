package com.playground.payment_db.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playground.payment_db.infrastructure.persistence.entities.AbsaPaymentEntity;

@Repository
public interface JpaAbsaPaymentRepository extends JpaRepository<AbsaPaymentEntity, Long> {
    
}
