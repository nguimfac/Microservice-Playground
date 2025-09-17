package com.playground.payment_service.services;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.playground.payment_service.adpaters.inbound.OrangePaymentService;
import com.playground.payment_service.application.dto.OrangeMoneyPaymentRequest;
import com.playground.payment_service.application.dto.PaymentResponse;
import com.playground.payment_service.domain.ports.outbound.OrangeMoneyPaymentRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrangePaymentServiceTest {

    @Autowired
    OrangePaymentService service;

    @Autowired
    OrangeMoneyPaymentRepository repository;

    private OrangeMoneyPaymentRequest request;

    @BeforeEach
    void setUp() {
        request = new OrangeMoneyPaymentRequest(
            "om",
            "ORD-OM-1", 
            new BigDecimal("75.00"), 
            "Alice", 
            "+237600000000", 
            "alice@example.com"
        );
    }

    @Test
    @DisplayName("handlePayment() persiste et retourne response réelle")
    void handlePayment_ok() {
    PaymentResponse resp = service.handlePayment(request);
        assertThat(resp.paymentId()).isNotNull();
        assertThat(resp.status()).isEqualTo("PENDING");
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("checkPayment() retrouve le paiement persistant")
    void checkPayment_ok() {
    Long id = service.handlePayment(request).paymentId();
        PaymentResponse resp = service.checkPayment(id);
        assertThat(resp.paymentId()).isEqualTo(id);
        assertThat(resp.status()).isEqualTo("PENDING");
        assertThat(resp.message()).contains("retrieved");
    }

    @Test
    @DisplayName("checkPayment() -> not found provoque BusinessException")
    void checkPayment_notFound() {
        assertThatThrownBy(() -> service.checkPayment(9999L))
            .hasMessage("Orange Money payment not found with id : 9999");
    }
}
