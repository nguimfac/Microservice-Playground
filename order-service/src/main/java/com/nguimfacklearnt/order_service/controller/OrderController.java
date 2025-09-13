package com.nguimfacklearnt.order_service.controller;

import com.nguimfacklearnt.order_service.dto.OrderRequest;
import com.nguimfacklearnt.order_service.service.OrderService;
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
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = "inventory" , fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory" ,fallbackMethod = "fallBackMethod")
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        return orderService.placeOrder(orderRequest);
    }

    // est appelle l'orsque un appel ne reponds pas positivement
    public ResponseEntity<String> fallBackMethod(OrderRequest orderRequest, Throwable throwable) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Oops! Something went wrong. Please try again later.");
    }


}
