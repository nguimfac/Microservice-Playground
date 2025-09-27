package com.playground.order_service.dto.response;

public record CartItemResponse(
        Long id,
        Long productId,
        int quantity
) {}
