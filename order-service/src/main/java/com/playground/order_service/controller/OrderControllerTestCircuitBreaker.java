package com.playground.order_service.controller;

import com.playground.order_service.dto.request.OrderRequest;
import com.playground.order_service.dto.response.ApiResponse;
import com.playground.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/order/test-cb")
public class OrderControllerTestCircuitBreaker {

    private final OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(OrderControllerTestCircuitBreaker.class);


    public OrderControllerTestCircuitBreaker(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "retryFallback")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        logger.info(">>> Tentative d'appel Inventory");
        orderService.placeOrder(orderRequest);
        ApiResponse<?> response = new ApiResponse<>(orderRequest, "Order Placed Successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Retry(name = "inventory", fallbackMethod = "retryFallback")
    public ResponseEntity<?> cbFallback(Throwable t) {
        ApiResponse<?> response = new ApiResponse<>("Circuit ouvert ou erreur Inventory", t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }


    public ResponseEntity<?> retryFallback(RuntimeException e) {
        ApiResponse<?> response = new ApiResponse<>(e.getMessage(),
                "Inventory indisponible après tous les retries");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);

    }


}
