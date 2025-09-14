package com.playground.order_service.controller;

import com.playground.order_service.dto.OrderRequest;
import com.playground.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @Retry(name = "inventory", fallbackMethod = "fallBackMethod")
    //@TimeLimiter(name = "inventory", fallbackMethod = "fallBackMethod")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        String result = orderService.placeOrder(orderRequest);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<String> fallBackMethod(OrderRequest orderRequest, Throwable throwable) {
        String message;

        if (throwable instanceof java.util.concurrent.TimeoutException) {
            message = "Inventory service is taking too long to respond. Please try again later.";
        } else if (throwable instanceof RuntimeException) {
            message = "Inventory service is temporarily unavailable. Our team is working to resolve the issue.";
        } else {
            message = "An unexpected error occurred while processing your order. Please retry in a few moments.";
        }

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(message);
    }


}
