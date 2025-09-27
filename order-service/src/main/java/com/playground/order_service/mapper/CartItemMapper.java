package com.playground.order_service.mapper;

import com.playground.order_service.dto.response.CartItemResponse;
import com.playground.order_service.entities.cart.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemResponse toDto(CartItem entity);
}
