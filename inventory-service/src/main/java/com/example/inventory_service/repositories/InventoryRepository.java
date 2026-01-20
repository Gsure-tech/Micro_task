package com.example.inventory_service.repositories;

import com.example.inventory_service.entities.Inventory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(String productId);
}
