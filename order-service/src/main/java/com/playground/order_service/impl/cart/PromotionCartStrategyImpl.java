package com.playground.order_service.impl.cart;

import com.playground.order_service.model.cart.Cart;
import com.playground.order_service.service.CartStrategy;
import org.springframework.stereotype.Component;

@Component
public class PromotionCartStrategyImpl implements CartStrategy {
    @Override
    public Cart addProduct(long cartId, long productId, int quantity) {

        return null;
    }
}
