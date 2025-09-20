package com.playground.order_service.impl.cart;

import com.playground.order_service.model.cart.Cart;
import com.playground.order_service.dao.CartRepository;
import com.playground.order_service.service.CartStrategy;
import org.springframework.stereotype.Component;

@Component
public class DefaultCartStrategyImpl implements CartStrategy {

    private final CartRepository cartRepository;

    public DefaultCartStrategyImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @Override
    public Cart addProduct(long cartId, long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(new Cart());
        cart.addProductToCart(productId, quantity);
        return cartRepository.save(cart);
    }
}
