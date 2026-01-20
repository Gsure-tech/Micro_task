package com.example.order_service.controllers;

import com.example.order_service.dtos.ApiResponse;
import com.example.order_service.dtos.OrderRequest;
import com.example.order_service.entities.Order;
import com.example.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody OrderRequest request) {
        Order savedOrder = orderService.placeOrder(request);

        ApiResponse<Order> response = ApiResponse.<Order>builder()
                .status("SUCCESS")
                .message("Order placed successfully and inventory update triggered")
                .data(savedOrder)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}