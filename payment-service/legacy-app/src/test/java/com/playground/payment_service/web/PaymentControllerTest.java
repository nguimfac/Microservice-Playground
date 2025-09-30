package com.playground.payment_service.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import com.playground.payment_service.application.dto.AbsaPaymentRequest;
import com.playground.payment_service.application.dto.OrangeMoneyPaymentRequest;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PaymentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/payments (ABSA) -> 200")
    void pay_absa_ok() throws Exception {
        mockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(absaJson("ORD-10", 120.00, "John Doe", "123456", "ABSA")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").isNumber())
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @DisplayName("GET /api/payments/absa/{id} -> 200")
    void check_absa_ok() throws Exception {
        Long id = createPaymentAndReturnId(absaJson("ORD-11", 90.00, "Jane Doe", "999999", "ABSA"));

            mockMvc.perform(get("/api/payments/absa/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(id));
    }

    @Test
    @DisplayName("POST /api/payments (ORANGE) -> 200")
    void pay_orange_ok() throws Exception {
        String json = orangeJson("ORD-OM-55",75.50,"Alice","+237600000001","alice@example.com");
            mockMvc.perform(post("/api/payments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").isNumber())
                .andExpect(jsonPath("$.provider").value("ORANGE_MONEY"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @DisplayName("GET /api/payments/om/{id} -> 200")
    void check_orange_ok() throws Exception {
        Long id = createPaymentAndReturnId(orangeJson("ORD-OM-77",88.00,"Bob","+237600000777","bob@example.com"));

        mockMvc.perform(get("/api/payments/om/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.paymentId").value(id))
            .andExpect(jsonPath("$.provider").value("ORANGE_MONEY"));
    }
    
    //Mes Helpers
    private String absaJson(String orderId, double amount, String name, String account, String bank) throws Exception {
        AbsaPaymentRequest req = new AbsaPaymentRequest(
            "absa", orderId, BigDecimal.valueOf(amount), name, account, bank
        );
        return toJson(req);
    }

    private String orangeJson(String orderId, double amount, String name, String phone, String email) throws Exception {
        OrangeMoneyPaymentRequest req = new OrangeMoneyPaymentRequest(
            "om", orderId, BigDecimal.valueOf(amount), name, phone, email
        );
        return toJson(req);
    }

    private String toJson(Object o) throws Exception {
        return objectMapper.writeValueAsString(o);
    }

    private Long createPaymentAndReturnId(String json) throws Exception {
        String response = mockMvc.perform(post("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(response).get("paymentId").asLong();
    }
}
