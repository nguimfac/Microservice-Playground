package com.playground.inventory_service.controller;

import com.playground.dto.response.InventoryResponse;
import com.playground.inventory_service.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> skuCodes){
      List<InventoryResponse> responses =  inventoryService.isInStock(skuCodes);
      return ResponseEntity.ok(responses);
    }
}
