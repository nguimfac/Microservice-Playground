package com.playground.order_service.mapper;

import com.playground.order_service.dto.response.CartResponse;
import com.playground.order_service.entities.cart.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = CartItemMapper.class)
public interface CartMapper {
    CartResponse toDto(Cart entity);
}
