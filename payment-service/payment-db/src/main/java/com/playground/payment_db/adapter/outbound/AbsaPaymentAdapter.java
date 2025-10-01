package com.playground.payment_db.adapter.outbound;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.playground.payment.core.exceptions.BusinessException;
import com.playground.payment_db.infrastructure.persistence.entities.AbsaPaymentEntity;
import com.playground.payment_db.infrastructure.persistence.jpa.JpaAbsaPaymentRepository;
import com.playground.payment_db.port.outbound.AbsaPaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AbsaPaymentAdapter implements AbsaPaymentRepository {
    
    private final JpaAbsaPaymentRepository jpaRepository;

    private static final String NOT_FOUND_MSG_PREFIX = "ABSA payment not found with id : ";

    @Override
    public AbsaPaymentEntity save(AbsaPaymentEntity entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public AbsaPaymentEntity findById(Long id) {
        return jpaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND.value()));
    }

    @Override
    public List<AbsaPaymentEntity> findAll() {
        List<AbsaPaymentEntity> list = jpaRepository.findAll();
        if (list.isEmpty()) {
            throw new BusinessException("No ABSA payments found", HttpStatus.NOT_FOUND.value());
        }
        return list;
    }

    @Override
    public AbsaPaymentEntity update(Long id, AbsaPaymentEntity entity) {
        AbsaPaymentEntity existing = jpaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND.value()));
        BeanUtils.copyProperties(entity, existing);
        return jpaRepository.save(existing);
    }

    @Override
    public boolean deleteById(Long id) {
        AbsaPaymentEntity existing = jpaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND.value()));
        jpaRepository.delete(existing);
        return true;
    }
}
