package com.example.notification_service.kafka;

import com.example.notification_service.dto.OrderEvent;
import com.example.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "order-events", groupId = "notification-group",containerFactory = "kafkaListenerContainerFactory")
    public void consumeOrder(OrderEvent orderEvent) {
        try {
            notificationService.sendEmail(orderEvent);
        } catch (Exception e) {
            // Log but don’t crash the app
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}
