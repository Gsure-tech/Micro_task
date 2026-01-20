package com.example.order_service.dtos;

// Used for the incoming HTTP Request
public record OrderRequest(String productId, Integer quantity, Double price) {}

