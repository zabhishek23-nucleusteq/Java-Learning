package com.example.employee.Service;

import com.example.employee.DTO.EmployeeDto;

public interface IKafkaProducerService {
    void sendMessage(String topic, String message);

    void sendEmployeeEvent(EmployeeDto employeeDto);
}
