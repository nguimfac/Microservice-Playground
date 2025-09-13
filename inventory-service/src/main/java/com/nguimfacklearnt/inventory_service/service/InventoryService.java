package com.nguimfacklearnt.inventory_service.service;

import com.nguimfack.learnt.dto.response.InventoryResponse;
import com.nguimfacklearnt.inventory_service.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(iv->new InventoryResponse(iv.getSkuCode(), iv.getQuantity() > 0))
                .toList();
    }
}
