package com.playground.gateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

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

                // Route vers config-server
                .route("config-server", r -> r
                        .path("/config-server/**")
                        .uri("lb://config-server"))
                // Route vers discoveryserver-service static ressources
                .route("discovery-service-static", r -> r
                                .path("/eureka/**")
                                .uri("lb://discovery-service"))
                // Route vers discoveryserver-service
                .route("discovery-service", r -> r
                        .path("/eureka/web")
                        .filters(f -> f.setPath("/"))
                        .uri("lb://discovery-service")
                ).build();
    }

}
