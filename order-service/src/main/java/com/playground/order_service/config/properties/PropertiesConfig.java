package com.playground.order_service.config.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "inventory.service")
public class PropertiesConfig {
    private String inventoryUrl;

    // getter & setter
    public String getInventoryUrl() {
        return inventoryUrl;
    }

    public void setInventoryUrl(String inventoryUrl) {
        this.inventoryUrl = inventoryUrl;
    }
}
