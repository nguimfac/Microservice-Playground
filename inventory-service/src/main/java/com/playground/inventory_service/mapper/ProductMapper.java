package com.playground.inventory_service.mapper;

import com.playground.dto.request.ProductRequest;
import com.playground.dto.response.ProductResponse;
import com.playground.inventory_service.mapper.helper.CategoryHelper;
import com.playground.inventory_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = CategoryHelper.class)
public interface ProductMapper {
    @Mapping(source = "categoryId", target = "category")
    Product toEntity(ProductRequest productRequest);
    ProductResponse toResponse(Product product);
}
