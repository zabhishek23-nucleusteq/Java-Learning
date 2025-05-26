package com.example.order_service.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String orderId;
    private String userEmail;
    private String status; // e.g., "PLACED"
}
