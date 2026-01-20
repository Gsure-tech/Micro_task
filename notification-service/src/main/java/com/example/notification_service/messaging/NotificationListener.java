package com.example.notification_service.messaging;

import com.example.notification_service.events.OrderPlacedEvent;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class NotificationListener {

    @Bean
    public Consumer<OrderPlacedEvent> orderPlaced() {
        return event -> {
            log.info("***************************************************");
            log.info("NOTIFICATION SERVICE: Order #{} Received!", event.orderId());
            log.info("Sending confirmation email for Product: {}", event.productId());
            log.info("Email successfully sent to customer.");
            log.info("***************************************************");
        };
    }
}