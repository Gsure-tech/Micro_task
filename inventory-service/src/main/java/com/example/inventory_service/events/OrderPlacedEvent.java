package com.example.inventory_service.events;

public record OrderPlacedEvent(
        Long orderId,
        String productId,
        Integer quantity,
        Double totalPrice
) {}