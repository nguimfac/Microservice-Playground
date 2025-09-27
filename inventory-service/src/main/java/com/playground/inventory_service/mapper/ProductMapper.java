package com.playground.inventory_service.mapper;


import com.playground.inventory_service.api.model.ProductRequest;
import com.playground.inventory_service.api.model.ProductResponse;
import com.playground.inventory_service.mapper.helper.CategoryHelper;
import com.playground.inventory_service.entities.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = CategoryHelper.class)
public interface ProductMapper {
    @Mapping(source = "categoryId", target = "category")
    Product toEntity(ProductRequest productRequest);
    ProductResponse toResponse(Product product);
}
