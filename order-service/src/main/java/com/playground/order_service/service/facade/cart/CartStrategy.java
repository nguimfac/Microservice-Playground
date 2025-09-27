package com.playground.order_service.service.facade.cart;

import com.playground.order_service.entities.cart.Cart;

public interface CartStrategy {
    Cart addProduct(long cartId, long productId, int quantity);
}
