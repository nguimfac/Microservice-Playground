package com.playground.inventory_service.mapper;

import com.playground.dto.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse productToProductResponse(Product)
}
