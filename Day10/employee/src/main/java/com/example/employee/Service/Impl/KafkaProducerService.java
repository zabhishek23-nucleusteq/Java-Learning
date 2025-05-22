package com.example.employee.Service.Impl;


import com.example.employee.DTO.EmployeeDto;
import com.example.employee.Service.IKafkaProducerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService implements IKafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, EmployeeDto> kafkaTemplateObject;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, KafkaTemplate<String, EmployeeDto> kafkaTemplateObject) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplateObject = kafkaTemplateObject;
    }

    public void sendMessage(String topic, String message) {
        this.kafkaTemplate.send(topic, message);
        System.out.println("Sent Kafka message to topic '" + topic + "': " + message);
    }

    public void sendEmployeeEvent(EmployeeDto employeeDto) {
        this.kafkaTemplateObject.send("employee-dto-topic", employeeDto);
        System.out.println("Sent Kafka message to topic 'employee-topic ': " + employeeDto.toString());
    }
}
