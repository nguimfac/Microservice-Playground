package com.playground.payment_api.infrastructure.adapters.outbound;

import com.playground.payment_api.domain.dto.NotificationRequest;
import com.playground.payment_api.domain.dto.NotificationResponse;
import com.playground.payment_api.domain.dto.NotificationStatus;
import com.playground.payment_api.domain.exceptions.NotificationServiceException;
import com.playground.payment_api.domain.ports.outbound.NotificationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceAdapter implements NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceAdapter.class);
    
    private final NotificationServiceFeignClient notificationServiceFeignClient;
    
    @Override
    @CircuitBreaker(name = "notification-service", fallbackMethod = "createNotificationFallback")
    @Retry(name = "notification-service")
    public NotificationResponse createNotification(NotificationRequest notificationRequest) {
        logger.info("Creating notification for recipient: {}", notificationRequest.getRecipient());
        
        try {
            return notificationServiceFeignClient.createNotification(notificationRequest);
        } catch (Exception e) {
            logger.error("Failed to create notification for recipient: {} with subject: '{}'. Operation: CREATE_NOTIFICATION", 
                        notificationRequest.getRecipient(), notificationRequest.getSubject(), e);
            throw new NotificationServiceException(
                "Failed to create notification via external service",
                "CREATE_NOTIFICATION"
            );
        }
    }
    
    @Override
    @CircuitBreaker(name = "notification-service", fallbackMethod = "getNotificationByIdFallback")
    @Retry(name = "notification-service")
    public Optional<NotificationResponse> getNotificationById(String notificationId) {
        logger.info("Retrieving notification with ID: {}", notificationId);
        try {
            NotificationResponse response = notificationServiceFeignClient.getNotificationById(notificationId);
            return Optional.ofNullable(response);
        } catch (Exception e) {
            logger.error("Error retrieving notification with ID {}: {}", notificationId, e.getMessage(), e);
            throw e;
        }
    }
    
    @Override
    @CircuitBreaker(name = "notification-service", fallbackMethod = "getNotificationsByRecipientFallback")
    @Retry(name = "notification-service")
    public List<NotificationResponse> getNotificationsByRecipient(String recipient) {
        logger.info("Retrieving notifications for recipient: {}", recipient);
        
        try {
            return notificationServiceFeignClient.getNotificationsByRecipient(recipient);
        } catch (Exception e) {
            logger.error("Error retrieving notifications for recipient {}: {}", recipient, e.getMessage(), e);
            throw e;
        }
    }
    
    @Override
    @CircuitBreaker(name = "notification-service", fallbackMethod = "getAllNotificationsFallback")
    @Retry(name = "notification-service")
    public List<NotificationResponse> getAllNotifications() {
        logger.info("Retrieving all notifications");
        
        try {
            return notificationServiceFeignClient.getAllNotifications();
        } catch (Exception e) {
            logger.error("Error retrieving all notifications: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    @Override
    @CircuitBreaker(name = "notification-service", fallbackMethod = "updateNotificationStatusFallback")
    @Retry(name = "notification-service")
    public boolean updateNotificationStatus(String notificationId, NotificationStatus status) {
        logger.info("Updating notification {} status to: {}", notificationId, status);
        
        try {
            Boolean result = notificationServiceFeignClient.updateNotificationStatus(notificationId, status.name());
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error updating notification status for ID {}: {}", notificationId, e.getMessage(), e);
            throw e;
        }
    }
    
    public NotificationResponse createNotificationFallback(NotificationRequest request, Exception ex) {
        logger.error("Circuit breaker activated: Unable to create notification. Error: {}", ex.getMessage());
        return new NotificationServiceFallback().createNotification(request);
    }
    
    public Optional<NotificationResponse> getNotificationByIdFallback(String id, Exception ex) {
        logger.error("Circuit breaker activated: Unable to get notification {}. Error: {}", id, ex.getMessage());
        return Optional.empty();
    }
    
    public List<NotificationResponse> getNotificationsByRecipientFallback(String recipient, Exception ex) {
        logger.error("Circuit breaker activated: Unable to get notifications for {}. Error: {}", recipient, ex.getMessage());
        return new NotificationServiceFallback().getNotificationsByRecipient(recipient);
    }
    
    public List<NotificationResponse> getAllNotificationsFallback(Exception ex) {
        logger.error("Circuit breaker activated: Unable to get all notifications. Error: {}", ex.getMessage());
        return new NotificationServiceFallback().getAllNotifications();
    }
    
    public boolean updateNotificationStatusFallback(String id, NotificationStatus status, Exception ex) {
        logger.error("Circuit breaker activated: Unable to update notification {} status. Error: {}", id, ex.getMessage());
        return false;
    }
}