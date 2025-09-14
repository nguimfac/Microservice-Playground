package com.playground.inventory_service.service;

import com.playground.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    public List<InventoryResponse> isInStock(List<String> skuCode);

    }