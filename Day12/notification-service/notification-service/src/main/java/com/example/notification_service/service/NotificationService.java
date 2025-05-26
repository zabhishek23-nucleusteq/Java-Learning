package com.example.notification_service.service;

import com.example.notification_service.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void sendEmail(OrderEvent event) {
        log.info("Sending confirmation email to: {}", event.getUserEmail());
        log.info("Order ID: {}, Status: {}", event.getOrderId(), event.getStatus());
        // Here you can integrate actual email service (e.g. JavaMailSender)
    }
}
