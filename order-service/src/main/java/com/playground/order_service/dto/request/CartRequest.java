package com.playground.order_service.dto.request;

import com.playground.order_service.model.cart.CartItem;

import java.util.List;

public record CartRequest(long id , long ownerId, List<CartItem> cartItems) {
}
