package com.playground.payment_service.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.playground.payment_service.application.dto.AbsaPaymentRequest;
import com.playground.payment_service.domain.models.AbsaPaymentEntity;

@Mapper(componentModel = "spring")
public interface AbsaPaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    AbsaPaymentEntity toEntity(AbsaPaymentRequest request);
}
