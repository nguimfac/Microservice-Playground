package com.playground.payment_db.steps;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import com.playground.payment_db.infrastructure.persistence.entities.AbsaPaymentEntity;

import io.cucumber.java.DataTableType;

public class CucumberMappingConfig {
    
    
    @DataTableType
    public AbsaPaymentEntity convert(Map<String, String> entry) {
        AbsaPaymentEntity entity = new AbsaPaymentEntity();
        entity.setId(entry.get("id") != null ? Long.valueOf(entry.get("id")) : null);
        entity.setOrderId(entry.get("orderId"));
        entity.setAmount(entry.get("amount") != null ? new BigDecimal(entry.get("amount")) : null);
        entity.setStatus(entry.get("status"));
        entity.setCreatedAt(entry.get("createdAt") != null ? LocalDateTime.parse(entry.get("createdAt")) : null);
        entity.setBenificairyName(entry.get("benificairyName"));

        entity.setBenificairyAccountNumber(entry.get("benificairyAccountNumber"));
        entity.setBeneficiaryBankName(entry.get("beneficiaryBankName"));

        return entity;
    }
}
