package com.example.employeegcp.Repository;

import com.example.employeegcp.Entity.Employee;
import com.google.cloud.spring.data.spanner.repository.SpannerRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends SpannerRepository<Employee, String> {
}