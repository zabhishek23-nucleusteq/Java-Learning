package com.example.EmployeeApplication.Service;

import com.example.EmployeeApplication.DTO.EmployeeDto;
import com.example.EmployeeApplication.Entity.Employee;
import com.example.EmployeeApplication.Exception.ResourceNotFoundException;
import com.example.EmployeeApplication.Repository.EmployeeRepository;
import com.example.EmployeeApplication.Service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Array;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeRepository employeeRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private EmployeeServiceImpl employeeService;

    @Test
    void testAddEmployee()
    {
        EmployeeDto dto =  new EmployeeDto("Abhishek","abhishek@gmail.com","IT",2000F);
        Employee emp = new Employee(1,"Abhishek","abhishek@gmail.com","IT",2000F);
        when(modelMapper.map(dto,Employee.class)).thenReturn(emp);
        when(employeeRepository.save(emp)).thenReturn(emp);
        when(modelMapper.map(emp,EmployeeDto.class)).thenReturn(dto);
        EmployeeDto result = employeeService.addEmployee(dto);
        assertEquals("Abhishek",result.getName());
        verify(employeeRepository,times(1)).save(emp);
    }


    @Test
    void testGetAllEmployees() {
        Employee emp = new Employee(1, "Abhishek", "abhishek@gmail.com", "IT", 2000F);
        EmployeeDto dto = new EmployeeDto("Abhishek", "abhishek@gmail.com", "IT", 2000F);

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(emp));
        when(modelMapper.map(emp, EmployeeDto.class)).thenReturn(dto);

        List<EmployeeDto> result = employeeService.getAllEmployees();

        assertEquals(1, result.size());
        assertEquals("Abhishek", result.get(0).getName());
        verify(employeeRepository, times(1)).findAll();
    }
    @Test
    void testDeleteEmployee()
    {
        Employee emp = new Employee(1, "Aditya", "aditya@mail.com", "Tech", 75000f);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(emp));

        String result = employeeService.deleteEmployee(1);

        assertEquals("Employee with ID 1 deleted successfully.", result);
        verify(employeeRepository, times(1)).delete(emp);
    }

    @Test
    public void testUpdateEmployee_whenValidId_thenReturnsUpdatedDto() {
        int id = 1;
        Employee existing = new Employee(id, "Aditya", "aditya@example.com", "HR", 50000f);
        EmployeeDto updateDto = new EmployeeDto("Aakash", "aakash@example.com", "Finance", 60000f);
        Employee updated = new Employee(id, "Aakash", "aakash@example.com", "Finance", 60000f);

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(existing)).thenReturn(updated);

        EmployeeDto result = employeeService.updateEmployee(id, updateDto);

        assertEquals("Aakash", result.getName());
        assertEquals("aakash@example.com", result.getEmail());
        assertEquals("Finance", result.getDepartment());
    }

    @Test
    public void testUpdateEmployee_whenInvalidId_thenThrowsException() {
        int id = 2;
        EmployeeDto updateDto = new EmployeeDto("Test", "test@example.com", "IT", 55000f);

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(id, updateDto));
    }
}
