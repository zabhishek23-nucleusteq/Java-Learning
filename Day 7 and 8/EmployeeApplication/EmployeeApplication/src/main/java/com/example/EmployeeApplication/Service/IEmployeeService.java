package com.example.EmployeeApplication.Service;

import com.example.EmployeeApplication.DTO.EmployeeDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEmployeeService {
    EmployeeDto addEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto updateEmployee(int id, EmployeeDto employeeDto);
    String deleteEmployee(int id);
    String addEmployeesUsingExcel(MultipartFile multipartFile) throws IOException;
}
