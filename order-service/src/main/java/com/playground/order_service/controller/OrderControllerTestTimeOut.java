package com.playground.order_service.controller;

import com.playground.order_service.service.OrderService;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
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

    @TimeLimiter(name = "inventory", fallbackMethod = "timeoutFallback")
    @PostMapping
    public CompletableFuture<String> testTimeout() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000); // Simule un service lent (5s)
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Réponse après délai";
        });
    }

    public CompletableFuture<String> timeoutFallback(Throwable t) {
        return CompletableFuture.completedFuture("Inventory trop lent, timeout dépassé");
    }



}
