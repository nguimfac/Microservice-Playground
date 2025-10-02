package com.playground.payment_api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class NotificationRequest {
    private String recipient;
    private String subject;
    private String message;
    private NotificationType type;
}