package com.example.employee.Mapper;

import com.example.employee.DTO.EmployeeDto;
import com.example.employee.Entity.Employee;

public class EmployeeMapper {
    public static EmployeeDto toDto(Employee employee)
    {
        return new EmployeeDto(employee.getName(), employee.getEmail(), employee.getDepartment(), employee.getSalary());
    }
    public static Employee toEntity(EmployeeDto employeeDto)
    {
        return new Employee(employeeDto.getName(), employeeDto.getEmail(), employeeDto.getDepartment(), employeeDto.getSalary());
    }
}
