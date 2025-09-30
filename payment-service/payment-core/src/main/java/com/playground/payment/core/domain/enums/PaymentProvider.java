package com.playground.payment.core.domain.enums;

public enum PaymentProvider {
    ABSA("ABSA"),
    ORANGE_MONEY("Orange Money");

    private final String displayName;

    PaymentProvider(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}