package com.playground.gateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route vers inventory-service
                .route("inventory-service", r -> r
                        .path("/api/inventory/**")
                        .uri("lb://inventory-service"))

                // Route vers order-service
                .route("order-service", r -> r
                        .path("/api/order/**")
                        .uri("lb://order-service"))

                .route("eureka-web", r -> r
                        .path("/eureka/web")
                        .filters(f -> f.setPath("/"))  // HTML servi depuis /
                        .uri("http://localhost:8761")
                )
                // Route pour fichiers statiques (CSS, JS, images)
                .route("eureka-static", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761")  // pas de setPath
                ).build();
    }

}
