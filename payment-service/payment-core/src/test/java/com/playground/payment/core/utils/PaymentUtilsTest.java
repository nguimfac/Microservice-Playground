package com.playground.payment.core.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.*;

class PaymentUtilsTest {

    @Test
    @DisplayName("generatePaymentReference - doit créer une référence avec le format correct")
    void testGeneratePaymentReference() {

        String provider = "absa";
        
        String result = PaymentUtils.generatePaymentReference(provider);
        
        assertThat(result)
            .isNotNull()
            .startsWith("ABSA_")
            .contains("_");
        
        String[] parts = result.split("_");
        assertThat(parts)
            .hasSize(3)
            .satisfies(partsArray -> {
                assertThat(partsArray[0]).isEqualTo("ABSA");
                assertThat(partsArray[1]).hasSize(14);
                assertThat(partsArray[2]).hasSize(8);
            });
    }

    @Test
    @DisplayName("generatePaymentReference - doit gérer différents providers")
    void testGeneratePaymentReferenceWithDifferentProviders() {
        
        String absaRef = PaymentUtils.generatePaymentReference("absa");
        String orangeRef = PaymentUtils.generatePaymentReference("orange_money");
        
        assertThat(absaRef).startsWith("ABSA_");
        assertThat(orangeRef).startsWith("ORANGE_MONEY_");
        assertThat(absaRef).isNotEqualTo(orangeRef);
    }

    @Test
    @DisplayName("generateTransactionId - doit créer un UUID unique")
    void testGenerateTransactionId() {
        
        String id1 = PaymentUtils.generateTransactionId();
        String id2 = PaymentUtils.generateTransactionId();
        
        assertThat(id1)
            .isNotNull()
            .hasSize(36)
            .contains("-")
            .matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
        
        assertThat(id2)
            .isNotNull()
            .isNotEqualTo(id1);
    }

    @Test
    @DisplayName("formatAmount - doit formater avec 2 décimales")
    void testFormatAmount() {
        assertThat(PaymentUtils.formatAmount(100.0)).isEqualTo("100.00");
        assertThat(PaymentUtils.formatAmount(123.456)).isEqualTo("123.46"); // Arrondi
        assertThat(PaymentUtils.formatAmount(50.1)).isEqualTo("50.10");     // Ajoute zéro
        assertThat(PaymentUtils.formatAmount(0.99)).isEqualTo("0.99");
        assertThat(PaymentUtils.formatAmount(1000000.0)).isEqualTo("1000000.00");
    }

    @Test
    @DisplayName("formatAmount - doit gérer les cas limites")
    void testFormatAmountEdgeCases() {
        assertThat(PaymentUtils.formatAmount(null)).isEqualTo("0.00");
        assertThat(PaymentUtils.formatAmount(0.0)).isEqualTo("0.00");
    }

    @Test
    @DisplayName("maskPhoneNumber - doit masquer correctement les numéros")
    void testMaskPhoneNumber() {
        assertThat(PaymentUtils.maskPhoneNumber("237123456789")).isEqualTo("********6789");
        assertThat(PaymentUtils.maskPhoneNumber("+237123456789")).isEqualTo("*********6789");
        assertThat(PaymentUtils.maskPhoneNumber("123456789")).isEqualTo("*****6789");
        assertThat(PaymentUtils.maskPhoneNumber("1234567")).isEqualTo("***4567");
    }

    @Test
    @DisplayName("maskPhoneNumber - doit gérer les cas limites")
    void testMaskPhoneNumberEdgeCases() {
        assertThat(PaymentUtils.maskPhoneNumber("123")).isEqualTo("****");
        assertThat(PaymentUtils.maskPhoneNumber("12")).isEqualTo("****");
        assertThat(PaymentUtils.maskPhoneNumber("")).isEqualTo("****");
        assertThat(PaymentUtils.maskPhoneNumber(null)).isEqualTo("****");
    }

    @Test
    @DisplayName("Tous les utilitaires - test d'intégration")
    void testAllUtilitiesTogether() {
        String provider = "absa";
        Double amount = 1234.567;
        String phone = "237123456789";
        
        String paymentRef = PaymentUtils.generatePaymentReference(provider);
        String transactionId = PaymentUtils.generateTransactionId();
        String formattedAmount = PaymentUtils.formatAmount(amount);
        String maskedPhone = PaymentUtils.maskPhoneNumber(phone);
        
        assertThat(paymentRef).startsWith("ABSA_");
        assertThat(transactionId).hasSize(36);
        assertThat(formattedAmount).isEqualTo("1234.57");
        assertThat(maskedPhone).isEqualTo("********6789");
        
        assertThat(paymentRef).as("Référence de paiement").isNotBlank();
        assertThat(transactionId).as("ID de transaction").isNotBlank();
        assertThat(formattedAmount).as("Montant formaté").isNotBlank();
        assertThat(maskedPhone).as("Téléphone masqué").isNotBlank();
    }
}
