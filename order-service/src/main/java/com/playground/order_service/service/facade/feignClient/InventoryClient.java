package com.playground.order_service.service.facade.feignClient;

import com.playground.dto.response.InventoryResponse;
import com.playground.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@FeignClient(name = "inventory-service", url = "lb://inventory-service")
@FeignClient(name = "inventory-service", url = "http://localhost:8080/api/inventory")

public interface InventoryClient {
    @GetMapping
    List<InventoryResponse> checkStock(@RequestParam List<String> skuCodes);

    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable("id") long id);


}