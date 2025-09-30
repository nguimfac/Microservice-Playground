package com.playground.payment.core.constants;

public final class PaymentConstants {
    
    private PaymentConstants() {
    }
    
    // API Endpoints
    public static final String API_V1_PREFIX = "/api/v1";
    public static final String PAYMENTS_ENDPOINT = "/payments";
    
    // Error Messages
    public static final String ERROR_INVALID_PAYMENT_REQUEST = "Invalid payment request";
    public static final String ERROR_PAYMENT_NOT_FOUND = "Payment not found";
    public static final String ERROR_PAYMENT_PROCESSING_FAILED = "Payment processing failed";
    
    // Validation
    public static final int MIN_AMOUNT = 1;
    public static final int MAX_AMOUNT = 1000000;
    public static final int MAX_PHONE_LENGTH = 15;
    public static final int MIN_PHONE_LENGTH = 9;
}