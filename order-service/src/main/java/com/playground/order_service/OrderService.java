package com.playground.order_service;

import com.playground.order_service.dto.request.OrderRequest;
import com.playground.order_service.model.order.Order;

public interface OrderService {
    public Order placeOrder(OrderRequest orderRequest);
    }
