package com.playground.order_service.service;

import com.playground.order_service.client.PaymentClient;
import com.playground.order_service.dto.request.PaymentRequests.AbsaPaymentRequest;
import com.playground.order_service.dto.request.PaymentRequests.OrangeMoneyPaymentRequest;
import com.playground.order_service.dto.response.PaymentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayService {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayService.class);
    private final PaymentClient paymentClient;

    public PaymentGatewayService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @CircuitBreaker(name = "payment-service", fallbackMethod = "fallback")
    public PaymentResponse payByAbsa(AbsaPaymentRequest request) {
        AbsaPaymentRequest enriched = new AbsaPaymentRequest("absa", request.orderId(), request.amount(),
                request.benificairyName(), request.benificairyAccountNumber(), request.beneficiaryBankName());
        log.info("Calling payment-service (ABSA)");
        return paymentClient.payAbsa(enriched);
    }

    @CircuitBreaker(name = "payment-service", fallbackMethod = "fallback")
    public PaymentResponse payByOrangeMoney(OrangeMoneyPaymentRequest request) {
        OrangeMoneyPaymentRequest enriched = new OrangeMoneyPaymentRequest("om", request.orderId(), request.amount(),
                request.benificairyName(), request.phoneNumber(), request.emailAddress());
        log.info("Calling payment-service (Orange Money)");
        return paymentClient.payOrange(enriched);
    }

    // Fallback unique pour les deux méthodes (paramètre générique Object accepté par Resilience4j)
    public PaymentResponse fallback(Object request, Exception ex) {
        log.error("Fallback payment-service (request type={}): {}", 
                request != null ? request.getClass().getSimpleName() : "null", ex.getMessage());
        return new PaymentResponse(null, "FAILED", "Payment service unavailable", null, null, null);
    }
}
