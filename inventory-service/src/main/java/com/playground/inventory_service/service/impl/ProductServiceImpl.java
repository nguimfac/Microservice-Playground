package com.playground.inventory_service.service.impl;

import com.playground.dto.request.ProductRequest;
import com.playground.dto.response.InventoryResponse;
import com.playground.dto.response.ProductResponse;
import com.playground.exceptions.NoSuchElementFoundException;
import com.playground.inventory_service.mapper.ProductMapper;
import com.playground.inventory_service.model.product.Product;
import com.playground.inventory_service.dao.ProductRepository;
import com.playground.inventory_service.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return productRepository.findByproductCodeIn(skuCode).stream()
                .map(iv -> new InventoryResponse(iv.getProductCode(), iv.getQuantity() > 0))
                .toList();
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        log.info("Try creating product {}", productRequest);
        Product product = productMapper.toEntity(productRequest);
        productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse getProductById(long id) {
        log.info("Try getting product  with id {}", id);
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("No product found with id " + id));
        return productMapper.toResponse(product);
    }

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        log.info("Try getting all products");
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createDate")
        );
        Page<Product> productsPage = productRepository.findAll(sortedPageable);
        return productsPage.map(productMapper::toResponse);
    }

}