package com.playground.inventory_service.service;

import com.playground.inventory_service.api.model.ProductRequest;
import com.playground.inventory_service.api.model.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse getProductById(long id);
    Page<ProductResponse> getAllProducts(Pageable pageable);
}