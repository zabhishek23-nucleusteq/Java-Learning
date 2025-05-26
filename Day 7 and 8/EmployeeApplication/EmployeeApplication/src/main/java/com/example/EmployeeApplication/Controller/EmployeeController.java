package com.example.EmployeeApplication.Controller;

import com.example.EmployeeApplication.DTO.EmployeeDto;
import com.example.EmployeeApplication.Service.IEmployeeService;
import com.example.EmployeeApplication.Service.Impl.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {


    private final IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            EmployeeDto saved = employeeService.addEmployee(employeeDto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@RequestParam int id, @RequestBody EmployeeDto dto) {
        try {
            return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmployee(@RequestParam int id) {
        try {
            return ResponseEntity.ok(employeeService.deleteEmployee(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addEmployeesUsingDoc(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file provided");
        }
        try {
            String result = employeeService.addEmployeesUsingExcel(file);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
