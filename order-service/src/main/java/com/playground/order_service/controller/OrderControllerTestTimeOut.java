package com.playground.order_service.controller;

import com.playground.order_service.dto.request.OrderRequest;
import com.playground.order_service.dto.response.ApiResponse;
import com.playground.order_service.service.OrderService;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/order/test-timeout")
public class OrderControllerTestTimeOut {

    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderControllerTestTimeOut.class);

    public OrderControllerTestTimeOut(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @TimeLimiter(name = "inventory")
    public CompletableFuture<ResponseEntity<?>> placeOrder(@RequestBody OrderRequest orderRequest) {
        logger.info(">>> Tentative d'appel Inventory");
        return CompletableFuture.supplyAsync(() -> {
            orderService.placeOrder(orderRequest);
            ApiResponse<?> response = new ApiResponse<>(orderRequest, "Order Placed Successfully");
            return ResponseEntity.ok(response);
        });
    }


    public CompletableFuture<ResponseEntity<?>> cbFallback(OrderRequest orderRequest, Throwable ex) {
        logger.error(">>> Fallback déclenché : {}", ex.getMessage());
        ApiResponse<?> response = new ApiResponse<>(orderRequest, "Service Inventory indisponible ou délai dépassé");
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }




}
