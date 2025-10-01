package com.playground.payment_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "com.playground.payment_web",
    "com.playground.payment_api", 
    "com.playground.payment_db"
})
public class PaymentWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentWebApplication.class, args);
    }
}
