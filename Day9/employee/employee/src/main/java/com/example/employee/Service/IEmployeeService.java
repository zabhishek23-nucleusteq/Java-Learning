package com.example.employee.Service;

import com.example.employee.DTO.EmployeeDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IEmployeeService {
    String addEmployee(EmployeeDto employeeDto) throws JsonProcessingException;
}
