package com.playground.order_service.dto.response;

import com.playground.constant.CartStatusEnum;

import java.util.List;

public record CartResponse(
        Long id,
        Long ownerId,
        List<CartItemResponse> cartItems,
        CartStatusEnum cartStatusEnum

) {}
