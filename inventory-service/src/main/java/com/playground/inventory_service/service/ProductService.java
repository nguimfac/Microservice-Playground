package com.playground.inventory_service.service;

import com.playground.dto.request.ProductRequest;
import com.playground.dto.response.InventoryResponse;
import com.playground.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    public List<InventoryResponse> isInStock(List<String> skuCode);
    ProductResponse createProduct(ProductRequest productRequest);
}