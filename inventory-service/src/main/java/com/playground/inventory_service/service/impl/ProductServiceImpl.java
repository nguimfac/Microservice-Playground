package com.playground.inventory_service.service.impl;

import com.playground.dto.request.ProductRequest;
import com.playground.dto.response.InventoryResponse;
import com.playground.dto.response.ProductResponse;
import com.playground.inventory_service.repository.ProductRepository;
import com.playground.inventory_service.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<InventoryResponse> isInStock(List<String> skuCode){
        return productRepository.findByproductCode(skuCode).stream()
                .map(iv->new InventoryResponse(iv.getProductCode(), iv.getQuantity() > 0))
                .toList();
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        return null;
    }
}
