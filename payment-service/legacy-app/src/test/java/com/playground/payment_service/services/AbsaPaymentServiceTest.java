package com.playground.payment_service.services;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.playground.payment_service.adpaters.inbound.AbsaPaymentService;
import com.playground.payment_service.application.dto.AbsaPaymentRequest;
import com.playground.payment_service.application.dto.PaymentResponse;
import com.playground.payment_service.domain.ports.outbound.AbsaPaymentRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AbsaPaymentServiceTest {

    @Autowired
    AbsaPaymentService service;

    @Autowired
    AbsaPaymentRepository repository;

    private AbsaPaymentRequest request;

    @BeforeEach
    void setUp() {
        request = new AbsaPaymentRequest(
            "absa",
            "ORD-1", 
            new BigDecimal("50.00"), 
            "John Doe", 
            "123456", 
            "ABSA"
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
    @DisplayName("checkPayment() retrouve l'entité persistée")
    void checkPayment_ok() {
    Long id = service.handlePayment(request).paymentId();
        PaymentResponse resp = service.checkPayment(id);
        assertThat(resp.paymentId()).isEqualTo(id);
    }

    @Test
    @DisplayName("checkPayment() -> not found lève BusinessException")
    void checkPayment_notFound() {
        assertThatThrownBy(() -> service.checkPayment(9999L))
            .hasMessage("ABSA payment not found with id : 9999");
    }
}
