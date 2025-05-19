package com.example.EmployeeApplication.Controller;

import com.example.EmployeeApplication.DTO.EmployeeDto;
import com.example.EmployeeApplication.Service.IEmployeeService;
import com.example.EmployeeApplication.Service.Impl.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.MulticastChannel;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
        private final IEmployeeService employeeService;

        public EmployeeController(IEmployeeService employeeService) {
            this.employeeService = employeeService;
        }

        @PostMapping("/add")
        public ResponseEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto) {
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.addEmployee(employeeDto));
        }

        @GetMapping("/all")
        public ResponseEntity<?> getEmployees() {
            return ResponseEntity.ok(employeeService.getAllEmployees());
        }

        @PostMapping("/upload")
        public ResponseEntity<?> addEmployeesByDoc(@RequestParam("file") MultipartFile multipartFile) throws IOException {
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.addEmployeesUsingExcel(multipartFile));
        }

        @PutMapping("/update")
        public ResponseEntity<?> updateEmployee(@RequestParam int id, @RequestBody EmployeeDto employeeDto) {
            return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDto));
        }

        @DeleteMapping("/delete")
        public ResponseEntity<?> deleteEmployee(@RequestParam int id) {
            return ResponseEntity.ok(employeeService.deleteEmployee(id));
        }
    }

