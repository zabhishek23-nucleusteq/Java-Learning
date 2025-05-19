package com.example.employee.Service.Impl;

import com.example.employee.DTO.EmployeeDto;
import com.example.employee.Entity.Employee;
import com.example.employee.Feign.EmailClient;
import com.example.employee.Mapper.EmployeeMapper;
import com.example.employee.Repository.EmployeeRepository;
import com.example.employee.Service.IEmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmailClient emailClient;
    private final KafkaProducerService kafkaProducerService;

    public EmployeeService(EmployeeRepository employeeRepository, EmailClient emailClient, KafkaProducerService kafkaProducerService) {
        this.employeeRepository = employeeRepository;
        this.emailClient = emailClient;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    public String addEmployee(EmployeeDto employeeDto) throws JsonProcessingException {
        Employee employee = EmployeeMapper.toEntity(employeeDto);
        employeeRepository.save(employee);
        emailClient.sendEmail(employeeDto);
        String payload = new ObjectMapper().writeValueAsString(employeeDto);
        kafkaProducerService.sendMessage("employee-topic", payload);
        return "Employee created , email and event is sent to Kafka.";
    }
}
