package com.playground.payment.core.domain.enums;

public enum PaymentStatus {
    PENDING("Pending"),
    PROCESSING("Processing"),
    SUCCESS("Success"),
    FAILED("Failed"),
    CANCELLED("Cancelled");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}