package com.example.inventory_service.messaging;

import com.example.inventory_service.events.OrderPlacedEvent;
import com.example.inventory_service.services.InventoryService;
import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryListener {

    private final InventoryService inventoryService;

    public InventoryListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Bean
    public Consumer<OrderPlacedEvent> orderPlaced() {
        return event -> {
            System.out.println("Received message from RabbitMQ for Order: " + event.orderId());
            inventoryService.deductStock(event.productId(), event.quantity());
        };
    }
}