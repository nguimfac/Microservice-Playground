package com.playground.payment_api.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationServiceException extends RuntimeException {
    
    private final String operation;
    private final String recipient;

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder(super.getMessage());
        
        if (operation != null) {
            message.append(" [Operation: ").append(operation).append("]");
        }
        
        if (recipient != null) {
            message.append(" [Recipient: ").append(recipient).append("]");
        } 
        return message.toString();
    }
}