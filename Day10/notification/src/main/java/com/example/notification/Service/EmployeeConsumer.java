package com.example.notification.Service;

import com.example.notification.DTO.EmployeeDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConsumer {
    public EmployeeConsumer() {
    }

    @KafkaListener(
            topics = {"employee-dto-topic"},
            groupId = "notification-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(EmployeeDto employeeDto) {
        System.out.println("Received employee event: " + employeeDto);
    }
}
