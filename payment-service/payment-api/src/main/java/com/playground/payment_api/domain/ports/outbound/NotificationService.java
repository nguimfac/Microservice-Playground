package com.playground.payment_api.domain.ports.outbound;

import com.playground.payment_api.domain.dto.NotificationRequest;
import com.playground.payment_api.domain.dto.NotificationResponse;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    
    NotificationResponse createNotification(NotificationRequest notificationRequest);
    Optional<NotificationResponse> getNotificationById(String notificationId);
    List<NotificationResponse> getNotificationsByRecipient(String recipient);
    List<NotificationResponse> getAllNotifications();
    boolean updateNotificationStatus(String notificationId, com.playground.payment_api.domain.dto.NotificationStatus status);
}