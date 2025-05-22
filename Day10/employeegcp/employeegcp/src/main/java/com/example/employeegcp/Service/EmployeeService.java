package com.example.employeegcp.Service;
import com.example.employeegcp.Entity.Employee;
import com.example.employeegcp.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public Employee save(Employee employee) {
        try {
            return repository.save(employee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> getAll() {
        return (List<Employee>) repository.findAll();
    }

    public Employee getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}

