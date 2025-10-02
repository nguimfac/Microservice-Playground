package com.playground.payment_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.playground.payment_api.domain.ports.outbound")
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
