package com.playground.payment_api.domain.ports.outbound;

import com.playground.payment_api.domain.dto.NotificationRequest;
import com.playground.payment_api.domain.dto.NotificationResponse;

public interface NotificationService {
    
    NotificationResponse createNotification(NotificationRequest notificationRequest);
}