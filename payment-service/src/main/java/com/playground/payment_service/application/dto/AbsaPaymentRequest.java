package com.playground.payment_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AbsaPaymentRequest extends BasePaymentRequest {

    @NotBlank(message = "Benificiary account number is mandatory")
    private String benificairyAccountNumber;

    @NotBlank(message = "Beneficiary bank name is mandatory")
    private String beneficiaryBankName;
}
