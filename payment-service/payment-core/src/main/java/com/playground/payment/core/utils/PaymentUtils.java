package com.playground.payment.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

public final class PaymentUtils {
    
    private PaymentUtils() {
    }
    
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    
    public static String generatePaymentReference(String provider) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return String.format("%s_%s_%s", provider.toUpperCase(), timestamp, uuid);
    }
    
    public static String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
    
    public static String formatAmount(Double amount) {
        if (amount == null) {
            return "0.00";
        }
        return String.format(Locale.US, "%.2f", amount);  // Force le locale US (point)
    }
    
    public static String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 4) {
            return "****";
        }
        int visibleDigits = Math.min(4, phoneNumber.length());
        String visible = phoneNumber.substring(phoneNumber.length() - visibleDigits);
        return "*".repeat(phoneNumber.length() - visibleDigits) + visible;
    }
}