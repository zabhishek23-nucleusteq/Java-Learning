package com.example.employee.Service.Impl;


import com.example.employee.Service.IKafkaConsumerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService implements IKafkaConsumerService {
    public KafkaConsumerService() {
    }

    @KafkaListener(
            topics = {"employee-topic"},
            groupId = "employee-group"
    )
    public void consume(String message) {
        System.out.println("Consumed message from Kafka: " + message);
    }
}

