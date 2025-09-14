package com.playground.payment_service.application.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("absa")
public class AbsaPaymentRequest extends BasePaymentRequest {

    @NotBlank(message = "Benificiary account number is mandatory")
    private String benificairyAccountNumber;

    @NotBlank(message = "Beneficiary bank name is mandatory")
    private String beneficiaryBankName;
}
