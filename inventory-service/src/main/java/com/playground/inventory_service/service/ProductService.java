package com.playground.inventory_service.service;

import com.playground.dto.request.ProductRequest;
import com.playground.dto.response.InventoryResponse;
import com.playground.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    public List<InventoryResponse> isInStock(List<String> skuCode);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse getProductById(long id);
    Page<ProductResponse> getAllProducts(Pageable pageable);
}