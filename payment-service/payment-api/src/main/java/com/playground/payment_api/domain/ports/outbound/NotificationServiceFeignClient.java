package com.playground.payment_api.domain.ports.outbound;

import com.playground.payment_api.domain.dto.NotificationRequest;
import com.playground.payment_api.domain.dto.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(
    name = "notification-service",
    url = "${app.services.notification.url:http://localhost:8080}"
)
public interface NotificationServiceFeignClient {
    
    @PostMapping("/api/notifications")
    NotificationResponse createNotification(@RequestBody NotificationRequest request);
}