package com.example.order_service.kafka;

import com.example.order_service.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private static final String TOPIC = "order-events";

    public void sendOrderEvent(OrderEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}