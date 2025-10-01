package com.playground.payment_db.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.playground.payment_db.infrastructure.persistence.entities.OrangeMoneyEntity;


@Repository
public interface JpaOrangeMoneyPaymentRepository extends JpaRepository<OrangeMoneyEntity, Long> {
}
