package com.playground.payment_web.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.payment.core.application.dto.AbsaPaymentRequest;
import com.playground.payment.core.application.dto.BasePaymentRequest;
import com.playground.payment.core.application.dto.OrangeMoneyPaymentRequest;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@AutoConfigureWebMvc
public class PaymentDeserializationSteps {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Autowired
    private ObjectMapper objectMapper;

    private String requestJson;
    private MvcTestResult mockMvcResult;
    private BasePaymentRequest deserializedRequest;
    private Exception thrownException;

    @Given("I have an ABSA payment request JSON:")
    public void i_have_an_absa_payment_request_json(String json) {
        this.requestJson = json;
    }

    @Given("I have an Orange Money payment request JSON:")
    public void i_have_an_orange_money_payment_request_json(String json) {
        this.requestJson = json;
    }

    @Given("I have an invalid payment request JSON:")
    public void i_have_an_invalid_payment_request_json(String json) {
        this.requestJson = json;
    }

    @When("I send the payment request")
    public void i_send_the_payment_request() throws Exception {
        try {
            this.deserializedRequest = objectMapper.readValue(requestJson, BasePaymentRequest.class);
            
            this.mockMvcResult = mockMvcTester.post()
                    .uri("/api/payments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson)
                    .exchange();
                    
        } catch (Exception e) {
            this.thrownException = e;
        }
    }

    @Then("the request should be deserialized as AbsaPaymentRequest")
    public void the_request_should_be_deserialized_as_absa_payment_request() {
        assertThat(deserializedRequest).isInstanceOf(AbsaPaymentRequest.class);
    }

    @Then("the request should be deserialized as OrangeMoneyPaymentRequest")
    public void the_request_should_be_deserialized_as_orange_money_payment_request() {
        assertThat(deserializedRequest).isInstanceOf(OrangeMoneyPaymentRequest.class);
    }

    @Then("the payment request should have the following values:")
    public void the_payment_request_should_have_the_following_values(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        
        // On s'attend à une seule ligne de données
        assertThat(rows).hasSize(1);
        Map<String, String> expectedValues = rows.get(0);
        
        // Validation des propriétés communes
        if (expectedValues.containsKey("provider")) {
            assertThat(deserializedRequest.provider()).isEqualTo(expectedValues.get("provider"));
        }
        
        if (expectedValues.containsKey("orderId")) {
            assertThat(deserializedRequest.orderId()).isEqualTo(expectedValues.get("orderId"));
        }
        
        if (expectedValues.containsKey("amount")) {
            BigDecimal expectedAmount = new BigDecimal(expectedValues.get("amount"));
            assertThat(deserializedRequest.amount()).isEqualTo(expectedAmount);
        }
        
        // Validation des propriétés spécifiques ABSA
        if (deserializedRequest instanceof AbsaPaymentRequest absaRequest) {
            if (expectedValues.containsKey("benificairyAccountNumber")) {
                assertThat(absaRequest.benificairyAccountNumber())
                    .isEqualTo(expectedValues.get("benificairyAccountNumber"));
            }
            if (expectedValues.containsKey("beneficiaryBankName")) {
                assertThat(absaRequest.beneficiaryBankName())
                    .isEqualTo(expectedValues.get("beneficiaryBankName"));
            }
        }
        
        // Validation des propriétés spécifiques Orange Money
        if (deserializedRequest instanceof OrangeMoneyPaymentRequest omRequest) {
            if (expectedValues.containsKey("phoneNumber")) {
                assertThat(omRequest.phoneNumber())
                    .isEqualTo(expectedValues.get("phoneNumber"));
            }
            if (expectedValues.containsKey("emailAddress")) {
                assertThat(omRequest.emailAddress())
                    .isEqualTo(expectedValues.get("emailAddress"));
            }
        }
    }

    @Then("the request should fail with deserialization error")
    public void the_request_should_fail_with_deserialization_error() {
        assertThat(thrownException).isNotNull();
        assertThat(thrownException.getMessage()).containsAnyOf(
            "unknown_provider", 
            "Could not resolve type id", 
            "Invalid type id"
        );
    }
}
