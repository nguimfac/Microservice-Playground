package com.playground.inventory_service.repository;

import com.playground.inventory_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByproductCode(List<String> skuCode);
}
