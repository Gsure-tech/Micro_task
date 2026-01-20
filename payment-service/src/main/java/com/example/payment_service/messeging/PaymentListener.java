package com.example.payment_service.messeging;

import com.example.payment_service.entities.Payment;
import com.example.payment_service.events.OrderPlacedEvent;
import com.example.payment_service.repositories.PaymentRepository;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PaymentListener {

    private final PaymentRepository paymentRepository;

    @Bean
        public Consumer<OrderPlacedEvent> orderPlaced() {
            return event -> {
                log.info("Processing payment for Order ID: {}", event.orderId());

                Payment payment = Payment.builder()
                        .orderId(event.orderId())
                        .amount(event.totalPrice())
                        .status("SUCCESSFUL")
                        .transactionDate(LocalDateTime.now())
                        .build();

                paymentRepository.save(payment);
            };
        }
}