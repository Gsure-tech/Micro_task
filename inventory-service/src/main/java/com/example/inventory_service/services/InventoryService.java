package com.example.inventory_service.services;

import com.example.inventory_service.entities.Inventory;
import com.example.inventory_service.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository repository;

    @Transactional
    public Inventory deductStock(String productId, Integer quantity) { // 2. Changed return type
        log.info("Attempting to deduct {} from stock for product: {}", quantity, productId);

        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() -> {
                    log.error("Stock deduction failed: Product {} not found", productId);
                    return new RuntimeException("Product not found in Inventory: " + productId);
                });

        if (inventory.getStockLevel() < quantity) {
            log.warn("Insufficient stock for product: {}. Current: {}, Requested: {}",
                    productId, inventory.getStockLevel(), quantity);
            throw new RuntimeException("Not enough stock for: " + productId);
        }

        inventory.setStockLevel(inventory.getStockLevel() - quantity);
        Inventory updatedInventory = repository.save(inventory);

        log.info("Stock updated successfully. New level for {}: {}", productId, updatedInventory.getStockLevel());

        return updatedInventory;
    }
}