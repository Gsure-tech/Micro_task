package com.example.payment_service.events;

public record OrderPlacedEvent(
        Long orderId,
        String productId,
        Integer quantity,
        Double totalPrice
) {}