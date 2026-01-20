package com.example.order_service.services;

import com.example.order_service.events.OrderPlacedEvent;
import com.example.order_service.dtos.OrderRequest;
import com.example.order_service.entities.Order;
import com.example.order_service.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StreamBridge streamBridge;

    @Transactional
    public Order placeOrder(OrderRequest request) {
        Order order = new Order();
        order.setProductId(request.productId());
        order.setQuantity(request.quantity());
        order.setPrice(request.price());
        order.setStatus("PLACED");

        Order savedOrder = orderRepository.save(order);

        // Calculate total
        Double total = savedOrder.getPrice() * savedOrder.getQuantity();

        // Now send the FULL information
        OrderPlacedEvent event = new OrderPlacedEvent(
                savedOrder.getId(),
                savedOrder.getProductId(),
                savedOrder.getQuantity(),
                total
        );

        streamBridge.send("orderPlaced-out-0", event);
        return savedOrder;
    }
}