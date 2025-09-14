package com.playground.payment_service.application.exceptions;

public class UnknownProviderException extends RuntimeException {
    public UnknownProviderException(String provider) {
        super("Unknown payment provider: " + provider);
    }
}
