package com.playground.order_service.service.facade.cart;

import com.playground.dto.request.AddProductRequest;
import com.playground.order_service.dto.request.CartRequest;
import com.playground.order_service.dto.response.CartItemResponse;
import com.playground.order_service.dto.response.CartResponse;

import java.util.List;

public interface CartService {

     CartResponse validateCart(long cartId);

     CartResponse addProductToCart(long cartId, AddProductRequest addProductRequest);

     CartResponse createCart(CartRequest cartRequest);

     CartResponse  findCartById(long cartId);

     List<CartItemResponse> getCartItemsOfCart(long cartId);
    }
