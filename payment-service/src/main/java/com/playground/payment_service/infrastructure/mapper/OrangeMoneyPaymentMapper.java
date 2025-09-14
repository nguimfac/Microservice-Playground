package com.playground.payment_service.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.playground.payment_service.application.dto.OrangeMoneyPaymentRequest;
import com.playground.payment_service.domain.models.OrangeMoneyEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrangeMoneyPaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    OrangeMoneyEntity toEntity(OrangeMoneyPaymentRequest request);
}
