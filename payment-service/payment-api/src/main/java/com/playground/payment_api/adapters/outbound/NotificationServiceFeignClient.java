package com.playground.payment_api.adapters.outbound;

import com.playground.payment_api.domain.dto.NotificationRequest;
import com.playground.payment_api.domain.dto.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(
    name = "notification-service",
    url = "${app.services.notification.url:http://localhost:8080}"
)
public interface NotificationServiceFeignClient {
    
    @PostMapping("/api/notifications")
    NotificationResponse createNotification(@RequestBody NotificationRequest request);
    
    @GetMapping("/api/notifications/{id}")
    NotificationResponse getNotificationById(@PathVariable String id);
    
    @GetMapping("/api/notifications/recipient/{recipient}")
    List<NotificationResponse> getNotificationsByRecipient(@PathVariable String recipient);
    
    @GetMapping("/api/notifications")
    List<NotificationResponse> getAllNotifications();
    
    @PutMapping("/api/notifications/{id}/status")
    Boolean updateNotificationStatus(@PathVariable String id, @RequestParam("status") String status);
}