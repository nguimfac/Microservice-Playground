package com.playground.order_service.dto.response;

import java.util.List;

public record CartResponse(
        Long id,
        Long ownerId,
        List<CartItemResponse> cartItems
) {}
