package com.example.employee.Feign;

import com.example.employee.DTO.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "email", url = "http://localhost:8082")
public interface EmailClient {
    @PostMapping("/email")
    String sendEmail(@RequestBody EmployeeDto employeeDto);
}
