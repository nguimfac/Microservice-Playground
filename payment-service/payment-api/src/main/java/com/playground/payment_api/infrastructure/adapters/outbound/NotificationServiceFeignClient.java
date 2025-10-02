package com.playground.payment_api.infrastructure.adapters.outbound;

import com.playground.payment_api.domain.dto.NotificationRequest;
import com.playground.payment_api.domain.dto.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(
    name = "notification-service",
    url = "${app.services.notification.url:http://localhost:8083}",
    fallback = NotificationServiceFallback.class
)
public interface NotificationServiceFeignClient {
    
    @PostMapping("/api/notifications")
    NotificationResponse createNotification(@RequestBody NotificationRequest request);
    
    @GetMapping("/api/notifications/{id}")
    NotificationResponse getNotificationById(@PathVariable("id") String id);
    
    @GetMapping("/api/notifications/recipient/{recipient}")
    List<NotificationResponse> getNotificationsByRecipient(@PathVariable("recipient") String recipient);
    
    @GetMapping("/api/notifications")
    List<NotificationResponse> getAllNotifications();
    
    @PutMapping("/api/notifications/{id}/status")
    Boolean updateNotificationStatus(@PathVariable("id") String id, 
                                   @RequestParam("status") String status);
}