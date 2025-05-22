package com.example.notification.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private String name;
    private String email;
    private String department;
    private float salary;
}
