package com.playground.payment_service.domain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "absa_payments")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AbsaPaymentEntity extends PaymentEntity {
    private String benificairyAccountNumber;
    private String beneficiaryBankName;
}
