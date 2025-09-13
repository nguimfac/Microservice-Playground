package com.playground.payment_service.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.playground.payment_service.application.dto.AbsaPaymentRequest;
import com.playground.payment_service.domain.models.AbsaPaymentEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AbsaPaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    AbsaPaymentEntity toEntity(AbsaPaymentRequest request);
}
