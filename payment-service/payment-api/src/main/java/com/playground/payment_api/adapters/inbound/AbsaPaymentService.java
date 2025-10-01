package com.playground.payment_api.adapters.inbound;

import org.springframework.stereotype.Service;

import com.playground.payment.core.application.dto.AbsaPaymentRequest;
import com.playground.payment.core.application.dto.BasePaymentRequest;
import com.playground.payment.core.application.dto.PaymentResponse;
import com.playground.payment.core.exceptions.BusinessException;
import com.playground.payment.core.utils.PaymentUtils;
import com.playground.payment_api.domain.ports.inbound.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("absa")
public class AbsaPaymentService implements PaymentService {

    @Override
    public String authenticate(BasePaymentRequest request) {
        log.info("Authenticating ABSA payment request");
        
        if (!(request instanceof AbsaPaymentRequest)) {
            throw new BusinessException("Invalid request type for ABSA provider");
        }
        
        AbsaPaymentRequest absaRequest = (AbsaPaymentRequest) request;
        
        // Simulate authentication logic
        if (PaymentUtils.isValidPhoneNumber(absaRequest.getPhoneNumber())) {
            return "ABSA_AUTH_TOKEN_" + System.currentTimeMillis();
        }
        
        throw new BusinessException("ABSA authentication failed");
    }

    @Override
    public PaymentResponse processPayment(BasePaymentRequest request, String authToken) {
        log.info("Processing ABSA payment with token: {}", authToken);
        
        AbsaPaymentRequest absaRequest = (AbsaPaymentRequest) request;
        
        // Simulate payment processing
        return PaymentResponse.builder()
                .transactionId("ABSA_TXN_" + System.currentTimeMillis())
                .status("SUCCESS")
                .message("ABSA payment processed successfully")
                .amount(absaRequest.getAmount())
                .provider("absa")
                .build();
    }

    @Override
    public PaymentResponse checkPayment(Long paymentId) {
        log.info("Checking ABSA payment status for ID: {}", paymentId);
        
        // Simulate status check
        return PaymentResponse.builder()
                .transactionId("ABSA_TXN_" + paymentId)
                .status("COMPLETED")
                .message("ABSA payment completed")
                .provider("absa")
                .build();
    }
}
