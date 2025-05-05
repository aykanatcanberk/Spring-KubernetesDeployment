package com.canberk.microservices.inventory_service.service;

import com.canberk.microservices.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean avaibleStock(String code, Integer quantity) {
        return inventoryRepository.existsByCodeAndQuantity(code,quantity);
    }
}
