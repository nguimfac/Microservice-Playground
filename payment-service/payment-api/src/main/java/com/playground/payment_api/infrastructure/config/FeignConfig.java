package com.playground.payment_api.infrastructure.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.playground.payment_api.infrastructure.adapters.outbound")
public class FeignConfig {
}