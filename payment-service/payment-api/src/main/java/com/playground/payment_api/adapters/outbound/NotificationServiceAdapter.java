package com.playground.payment_api.adapters.outbound;

import com.playground.payment_api.domain.dto.NotificationRequest;
import com.playground.payment_api.domain.dto.NotificationResponse;
import com.playground.payment_api.domain.dto.NotificationStatus;
import com.playground.payment_api.domain.ports.outbound.NotificationService;
import com.playground.payment_api.infrastructure.exceptions.NotificationServiceException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
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
            logger.error("Failed to retrieve notification with ID: {}. Operation: GET_NOTIFICATION_BY_ID", 
                        notificationId, e);
            throw new NotificationServiceException(
                "Failed to retrieve notification by ID from external service",
                "GET_NOTIFICATION_BY_ID"
            );
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
            logger.error("Failed to retrieve notifications for recipient: {}. Operation: GET_NOTIFICATIONS_BY_RECIPIENT", 
                        recipient, e);
            throw new NotificationServiceException(
                "Failed to retrieve notifications by recipient from external service",
                "GET_NOTIFICATIONS_BY_RECIPIENT"
            );
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
            logger.error("Failed to retrieve all notifications. Operation: GET_ALL_NOTIFICATIONS", e);
            throw new NotificationServiceException(
                "Failed to retrieve all notifications from external service",
                "GET_ALL_NOTIFICATIONS"
            );
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
            logger.error("Failed to update notification status for ID: {} to status: {}. Operation: UPDATE_NOTIFICATION_STATUS", 
                        notificationId, status, e);
            throw new NotificationServiceException(
                "Failed to update notification status via external service",
                "UPDATE_NOTIFICATION_STATUS"
            );
        }
    }
    
    public NotificationResponse createNotificationFallback(NotificationRequest request, Exception ex) {
        logger.error("Circuit breaker activated: Unable to create notification for recipient: {}. Error: {}", 
                    request.getRecipient(), ex.getMessage());
        
        // Créer une réponse fallback mockée
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
    
    public Optional<NotificationResponse> getNotificationByIdFallback(String id, Exception ex) {
        logger.error("Circuit breaker activated: Unable to get notification {}. Error: {}", id, ex.getMessage());
        return Optional.empty();
    }
    
    public List<NotificationResponse> getNotificationsByRecipientFallback(String recipient, Exception ex) {
        logger.error("Circuit breaker activated: Unable to get notifications for recipient: {}. Error: {}", 
                    recipient, ex.getMessage());
        return Collections.emptyList();
    }
    
    public List<NotificationResponse> getAllNotificationsFallback(Exception ex) {
        logger.error("Circuit breaker activated: Unable to get all notifications. Error: {}", ex.getMessage());
        return Collections.emptyList();
    }
    
    public boolean updateNotificationStatusFallback(String id, NotificationStatus status, Exception ex) {
        logger.error("Circuit breaker activated: Unable to update notification {} status to {}. Error: {}", 
                    id, status, ex.getMessage());
        return false;
    }
}