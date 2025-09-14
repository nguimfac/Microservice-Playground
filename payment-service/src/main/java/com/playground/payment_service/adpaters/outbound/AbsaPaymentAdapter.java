package com.playground.payment_service.adpaters.outbound;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;

import com.playground.payment_service.domain.models.AbsaPaymentEntity;
import com.playground.payment_service.domain.ports.outbound.AbsaPaymentRepository;
import com.playground.payment_service.infrastructure.persistence.JpaAbsaPaymentRepository;
import com.playground.payment_service.infrastructure.exceptions.BusinessException;

import lombok.RequiredArgsConstructor;

@Component
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
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<AbsaPaymentEntity> findAll() {
        List<AbsaPaymentEntity> list = jpaRepository.findAll();
        if (list.isEmpty()) {
            throw new BusinessException("No ABSA payments found", HttpStatus.NOT_FOUND);
        }
        return list;
    }

    @Override
    public boolean deleteById(Long id) {
        AbsaPaymentEntity existing = jpaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND));
        jpaRepository.delete(existing);
        return true;
    }

    @Override
    public AbsaPaymentEntity update(Long id, AbsaPaymentEntity entity) {
        AbsaPaymentEntity existing = jpaRepository.findById(id)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_MSG_PREFIX + id, HttpStatus.NOT_FOUND));
        existing.setOrderId(entity.getOrderId());
        existing.setAmount(entity.getAmount());
        existing.setStatus(entity.getStatus());
        existing.setCreatedAt(entity.getCreatedAt());
        existing.setBenificairyName(entity.getBenificairyName());
        existing.setBenificairyAccountNumber(entity.getBenificairyAccountNumber());
        existing.setBeneficiaryBankName(entity.getBeneficiaryBankName());
        return jpaRepository.save(existing);
    }
}
