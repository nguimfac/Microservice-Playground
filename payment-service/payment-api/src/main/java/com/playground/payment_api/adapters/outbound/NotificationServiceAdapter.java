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
    
    public NotificationResponse createNotificationFallback(NotificationRequest request, Exception ex) {
        logger.error("Circuit breaker activated: Unable to create notification for recipient: {}. Error: {}", 
                    request.getRecipient(), ex.getMessage());
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
}