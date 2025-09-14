package com.playground.payment_service.adpaters.outbound;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;

import com.playground.payment_service.domain.models.OrangeMoneyEntity;
import com.playground.payment_service.domain.ports.outbound.OrangeMoneyPaymentRepository;
import com.playground.payment_service.infrastructure.persistence.JpaOrangeMoneyPaymentRepository;
import com.playground.payment_service.infrastructure.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrangeMoneyPaymentAdapter implements OrangeMoneyPaymentRepository {

    private final JpaOrangeMoneyPaymentRepository jpaRepository;
    private static final String NOT_FOUND_MSG_PREFIX = "Orange Money payment not found with id : ";

    @Override
    public OrangeMoneyEntity save(OrangeMoneyEntity entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public OrangeMoneyEntity findById(Long id) {
        return jpaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<OrangeMoneyEntity> findAll() {
        List<OrangeMoneyEntity> list = jpaRepository.findAll();
        if (list.isEmpty()) {
            throw new BusinessException("No Orange Money payments found", HttpStatus.NOT_FOUND);
        }
        return list;
    }

    @Override
    public boolean deleteById(Long id) {
        OrangeMoneyEntity existing = jpaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND));
        jpaRepository.delete(existing);
        return true;
    }

    @Override
    public OrangeMoneyEntity update(Long id, OrangeMoneyEntity entity) {
        OrangeMoneyEntity existing = jpaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND));
        // Update fields
        existing.setOrderId(entity.getOrderId());
        existing.setAmount(entity.getAmount());
        existing.setStatus(entity.getStatus());
        existing.setCreatedAt(entity.getCreatedAt());
        existing.setBenificairyName(entity.getBenificairyName());
        existing.setPhoneNumber(entity.getPhoneNumber());
        existing.setEmailAddress(entity.getEmailAddress());
        return jpaRepository.save(existing);
    }
}
