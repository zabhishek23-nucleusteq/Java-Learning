package com.example.employee.Service;

public interface IKafkaProducerService {
    void sendMessage(String topic, String message);
}
