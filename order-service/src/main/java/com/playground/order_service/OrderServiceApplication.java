package com.playground.order_service;

import com.playground.order_service.dao.CartRepository;
import com.playground.order_service.model.cart.Cart;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.playground.order_service.service.facade.feignClient")
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}


    @Bean
    CommandLineRunner run (CartRepository cartRepository){
        return args -> {
            cartRepository.save(new Cart());
        };
    }
}
