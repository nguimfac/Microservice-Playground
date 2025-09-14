package com.playground.payment_service.application.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("om")
public class OrangeMoneyPaymentRequest extends BasePaymentRequest {

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @Email(message = "Email address must be valid")
    @NotBlank(message = "Email address is mandatory")
    private String emailAddress;
}
