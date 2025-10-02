package com.playground.payment_api.infrastructure.adapters.outbound;

import com.playground.payment_api.domain.dto.NotificationRequest;
import com.playground.payment_api.domain.dto.NotificationResponse;
import com.playground.payment_api.domain.dto.NotificationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class NotificationServiceFallback implements NotificationServiceFeignClient {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceFallback.class);
    
    @Override
    public NotificationResponse createNotification(NotificationRequest request) {
        logger.warn("Fallback: Unable to create notification for recipient: {}", request.getRecipient());
        
        NotificationResponse fallbackResponse = new NotificationResponse();
        fallbackResponse.setId("fallback-" + System.currentTimeMillis());
        fallbackResponse.setRecipient(request.getRecipient());
        fallbackResponse.setSubject(request.getSubject());
        fallbackResponse.setMessage(request.getMessage());
        fallbackResponse.setType(request.getType());
        fallbackResponse.setStatus(NotificationStatus.FAILED);
        fallbackResponse.setCreatedAt(LocalDateTime.now());
        
        return fallbackResponse;
    }
    
    @Override
    public NotificationResponse getNotificationById(String id) {
        logger.warn("Fallback: Unable to retrieve notification with ID: {}", id);
        return null;
    }
    
    @Override
    public List<NotificationResponse> getNotificationsByRecipient(String recipient) {
        logger.warn("Fallback: Unable to retrieve notifications for recipient: {}", recipient);
        return Collections.emptyList();
    }
    
    @Override
    public List<NotificationResponse> getAllNotifications() {
        logger.warn("Fallback: Unable to retrieve all notifications");
        return Collections.emptyList();
    }
    
    @Override
    public Boolean updateNotificationStatus(String id, String status) {
        logger.warn("Fallback: Unable to update notification status for ID: {} to status: {}", id, status);
        return false;
    }
}