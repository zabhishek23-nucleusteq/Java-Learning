package com.example.email.Controller;

import com.example.email.DTO.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody EmployeeDto employeeDto) {
        // Simulate email logic
        System.out.println("Email sent to: " + employeeDto.getEmail());
        return ResponseEntity.ok("Email successfully sent to: " + employeeDto.getEmail());
    }
}
