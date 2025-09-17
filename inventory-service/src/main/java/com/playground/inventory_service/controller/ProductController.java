package com.playground.inventory_service.controller;

import com.playground.dto.response.InventoryResponse;
import com.playground.inventory_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> skuCodes){
      List<InventoryResponse> responses =  productService.isInStock(skuCodes);
      return ResponseEntity.ok(responses);
    }
}
