package com.canberk.microservices.inventory_service.controller;

import com.canberk.microservices.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean avaibleStock(@RequestParam String code, @RequestParam Integer quantity) {
        return inventoryService.avaibleStock(code, quantity);
    }

}

