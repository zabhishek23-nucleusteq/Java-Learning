package com.example.order_service.controller;

import com.example.order_service.dto.OrderEvent;
import com.example.order_service.kafka.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer producer;

    @PostMapping
    public String placeOrder(@RequestBody OrderEvent order) {
        order.setStatus("PLACED");
        producer.sendOrderEvent(order);
        return "Order placed and event published";
    }
}
