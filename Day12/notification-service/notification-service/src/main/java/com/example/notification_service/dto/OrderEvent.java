package com.example.notification_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String orderId;
    private String userEmail;
    private String status;
}
