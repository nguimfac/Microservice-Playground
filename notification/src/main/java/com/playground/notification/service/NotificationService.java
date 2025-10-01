package com.playground.notification.service;

import com.playground.notification.dto.NotificationRequest;
import com.playground.notification.model.Notification;
import com.playground.notification.model.NotificationStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {

    private final ConcurrentHashMap<String, Notification> notifications = new ConcurrentHashMap<>();

    public Notification createNotification(NotificationRequest request) {
        Notification notification = new Notification(
                request.getRecipient(),
                request.getSubject(),
                request.getMessage(),
                request.getType()
        );
        notification.setId(UUID.randomUUID().toString());
        
        // Simuler l'envoi de la notification
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        
        notifications.put(notification.getId(), notification);
        return notification;
    }

    public Notification getNotificationById(String id) {
        return notifications.get(id);
    }

    public List<Notification> getAllNotifications() {
        return new ArrayList<>(notifications.values());
    }

    public List<Notification> getNotificationsByRecipient(String recipient) {
        return notifications.values().stream()
                .filter(notification -> notification.getRecipient().equals(recipient))
                .toList();
    }

    public Notification updateNotificationStatus(String id, NotificationStatus status) {
        Notification notification = notifications.get(id);
        if (notification != null) {
            notification.setStatus(status);
            if (status == NotificationStatus.SENT || status == NotificationStatus.DELIVERED) {
                notification.setSentAt(LocalDateTime.now());
            }
        }
        return notification;
    }

    public boolean deleteNotification(String id) {
        return notifications.remove(id) != null;
    }
}