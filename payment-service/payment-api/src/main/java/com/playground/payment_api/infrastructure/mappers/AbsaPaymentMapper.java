package com.playground.payment_api.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.playground.payment.core.application.dto.AbsaPaymentRequest;
import com.playground.payment_db.infrastructure.persistence.entities.AbsaPaymentEntity;

@Mapper(componentModel = "spring")
public interface AbsaPaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    AbsaPaymentEntity toEntity(AbsaPaymentRequest request);
}
