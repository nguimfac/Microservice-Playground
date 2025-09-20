package com.playground.order_service.service.facade.feignClient;

import com.playground.dto.response.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service", url = "http://inventory-service")
public interface InventoryClient {
    @GetMapping("/api/inventory")
    List<InventoryResponse> checkStock(@RequestParam List<String> skuCodes);
}