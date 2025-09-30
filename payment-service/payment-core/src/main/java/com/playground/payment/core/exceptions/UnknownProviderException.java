package com.playground.payment.core.exceptions;

public class UnknownProviderException extends BusinessException {
    
    public UnknownProviderException(String provider) {
        super("Unknown payment provider: " + provider, 400); // BAD_REQUEST = 400
    }
}