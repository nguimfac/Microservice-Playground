package com.playground.order_service.controller;

import com.playground.order_service.dto.request.OrderRequest;
import com.playground.order_service.dto.response.ApiResponse;
import com.playground.order_service.service.OrderService;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order/test-retry")
public class OrderControllerTestRetry {

    private final OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderControllerTestRetry.class);

    public OrderControllerTestRetry(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Retry(name = "inventory", fallbackMethod = "retryFallback")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        logger.info(">>> Tentative d'appel Inventory");
        orderService.placeOrder(orderRequest);
        ApiResponse<?> response = new ApiResponse<>(orderRequest, "Order Placed Successfully");
        return ResponseEntity.ok(response);
    }





}
