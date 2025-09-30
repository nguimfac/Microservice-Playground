package com.playground.inventory_service.controller;

import com.playground.inventory_service.api.ProductApi;
import com.playground.inventory_service.api.model.ProductRequest;
import com.playground.inventory_service.api.model.ProductResponse;
import com.playground.inventory_service.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class ProductController  implements ProductApi{

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ProductResponse> createProduct(ProductRequest productRequest) {
       ProductResponse response = productService.createProduct(productRequest);
       return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getAllProducts(Integer page, Integer size) {
        Page<ProductResponse> pages = productService.getAllProducts(PageRequest.of(page, page));
        return new ResponseEntity<>(pages.getContent() , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponse> getProduct(Integer id) {
        ProductResponse productResponse = productService.getProductById(id);
        return new ResponseEntity<>(productResponse , HttpStatus.OK);
    }

}



