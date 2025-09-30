package com.playground.dto.request;

import com.playground.constant.CartStrategyEnum;

public record AddProductRequest(
    long productId,
    int quantity,
    CartStrategyEnum strategy){


    public AddProductRequest {
        if (strategy == null) {
            strategy = CartStrategyEnum.DEFAULT;
        }
    }
}
