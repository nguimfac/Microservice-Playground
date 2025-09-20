package com.playground.order_service.service.facade.cart;

import com.playground.dto.request.AddProductRequest;
import com.playground.order_service.dto.response.CartResponse;

public interface CartService {

     CartResponse validateCart(long cartId);

     CartResponse addProductToCart(long cartId, AddProductRequest addProductRequest);

    }
