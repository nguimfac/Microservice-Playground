package com.playground.payment_db.adapter.outbound;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.playground.payment.core.exceptions.BusinessException;
import com.playground.payment_db.infrastructure.persistence.entities.OrangeMoneyEntity;
import com.playground.payment_db.infrastructure.persistence.jpa.JpaOrangeMoneyPaymentRepository;
import com.playground.payment_db.port.outbound.OrangeMoneyPaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
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
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public List<OrangeMoneyEntity> findAll() {
        List<OrangeMoneyEntity> list = jpaRepository.findAll();
        if (list.isEmpty()) {
            throw new BusinessException("No Orange Money payments found", HttpStatus.NOT_FOUND.value());
        }
        return list;
    }

    @Override
    public OrangeMoneyEntity update(Long id, OrangeMoneyEntity entity) {
        OrangeMoneyEntity existing = jpaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND.value()));
        BeanUtils.copyProperties(entity, existing);
        return jpaRepository.save(existing);
    }

    @Override
    public boolean deleteById(Long id) {
        OrangeMoneyEntity existing = jpaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND.value()));
        jpaRepository.delete(existing);
        return true;
    }
}
