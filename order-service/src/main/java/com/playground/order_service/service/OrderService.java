package com.playground.order_service.service;

import com.playground.order_service.dto.request.OrderRequest;
import com.playground.order_service.model.Order;

public interface OrderService {
    public Order placeOrder(OrderRequest orderRequest);
    }
