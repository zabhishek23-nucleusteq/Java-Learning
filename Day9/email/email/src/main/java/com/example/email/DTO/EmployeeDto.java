package com.example.email.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String name;
    private String email;
    private String department;
    private float salary;
}
