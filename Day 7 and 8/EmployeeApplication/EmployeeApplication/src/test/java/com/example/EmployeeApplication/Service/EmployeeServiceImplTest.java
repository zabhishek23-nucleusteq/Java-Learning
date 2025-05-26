package com.example.EmployeeApplication.Service;

import com.example.EmployeeApplication.DTO.EmployeeDto;
import com.example.EmployeeApplication.Entity.Employee;
import com.example.EmployeeApplication.Exception.ResourceNotFoundException;
import com.example.EmployeeApplication.Repository.EmployeeRepository;
import com.example.EmployeeApplication.Service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    // Constants
    private static final int EMPLOYEE_ID = 1;
    private static final String EMPLOYEE_NAME = "Abhishek";
    private static final String EMPLOYEE_EMAIL = "abhishek@gmail.com";
    private static final String EMPLOYEE_DEPARTMENT = "IT";
    private static final Float EMPLOYEE_SALARY = 2000F;

    private static final int EMPLOYEE_ID_2 = 2;
    private static final String EMPLOYEE_NAME_2 = "John";
    private static final String EMPLOYEE_EMAIL_2 = "john@example.com";
    private static final String EMPLOYEE_DEPARTMENT_2 = "HR";
    private static final Float EMPLOYEE_SALARY_2 = 3000F;

    private EmployeeDto inputDto;
    private Employee employeeEntity;
    private EmployeeDto expectedDto;

    @BeforeEach
    void setUp() {
        inputDto = new EmployeeDto(EMPLOYEE_NAME, EMPLOYEE_EMAIL, EMPLOYEE_DEPARTMENT, EMPLOYEE_SALARY);
        employeeEntity = new Employee(EMPLOYEE_ID, EMPLOYEE_NAME, EMPLOYEE_EMAIL, EMPLOYEE_DEPARTMENT, EMPLOYEE_SALARY);
        expectedDto = new EmployeeDto(EMPLOYEE_NAME, EMPLOYEE_EMAIL, EMPLOYEE_DEPARTMENT, EMPLOYEE_SALARY);
    }

    @Test
    @DisplayName("Should successfully add employee when valid data provided")
    void shouldAddEmployee_WhenValidDataProvided() {
        when(modelMapper.map(inputDto, Employee.class)).thenReturn(employeeEntity);
        when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);
        when(modelMapper.map(employeeEntity, EmployeeDto.class)).thenReturn(expectedDto);

        EmployeeDto result = employeeService.addEmployee(inputDto);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedDto);

        verify(modelMapper).map(inputDto, Employee.class);
        verify(employeeRepository).save(employeeEntity);
        verify(modelMapper).map(employeeEntity, EmployeeDto.class);
        verifyNoMoreInteractions(employeeRepository, modelMapper);
    }

    @Test
    @DisplayName("Should return all employees when employees exist")
    void shouldReturnAllEmployees_WhenEmployeesExist() {
        List<Employee> employees = Arrays.asList(
                employeeEntity,
                new Employee(EMPLOYEE_ID_2, EMPLOYEE_NAME_2, EMPLOYEE_EMAIL_2, EMPLOYEE_DEPARTMENT_2, EMPLOYEE_SALARY_2)
        );
        EmployeeDto dto1 = expectedDto;
        EmployeeDto dto2 = new EmployeeDto(EMPLOYEE_NAME_2, EMPLOYEE_EMAIL_2, EMPLOYEE_DEPARTMENT_2, EMPLOYEE_SALARY_2);

        when(employeeRepository.findAll()).thenReturn(employees);
        when(modelMapper.map(employees.get(0), EmployeeDto.class)).thenReturn(dto1);
        when(modelMapper.map(employees.get(1), EmployeeDto.class)).thenReturn(dto2);

        List<EmployeeDto> result = employeeService.getAllEmployees();

        assertThat(result).containsExactly(dto1, dto2);
        verify(employeeRepository).findAll();
        verify(modelMapper).map(employees.get(0), EmployeeDto.class);
        verify(modelMapper).map(employees.get(1), EmployeeDto.class);
        verifyNoMoreInteractions(employeeRepository, modelMapper);
    }

    @Test
    @DisplayName("Should return empty list when no employees exist")
    void shouldReturnEmptyList_WhenNoEmployeesExist() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        List<EmployeeDto> result = employeeService.getAllEmployees();

        assertThat(result).isEmpty();
        verify(employeeRepository).findAll();
        verifyNoMoreInteractions(employeeRepository, modelMapper);
    }

    @Test
    @DisplayName("Should delete employee successfully when valid ID provided")
    void shouldDeleteEmployee_WhenValidIdProvided() {
        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeEntity));

        String result = employeeService.deleteEmployee(EMPLOYEE_ID);

        assertThat(result).isEqualTo("Employee with ID " + EMPLOYEE_ID + " deleted successfully.");
        verify(employeeRepository).findById(EMPLOYEE_ID);
        verify(employeeRepository).delete(employeeEntity);
        verifyNoMoreInteractions(employeeRepository, modelMapper);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when employee not found for deletion")
    void shouldThrowException_WhenEmployeeNotFoundForDeletion() {
        when(employeeRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.deleteEmployee(EMPLOYEE_ID));

        verify(employeeRepository).findById(EMPLOYEE_ID);
        verify(employeeRepository, never()).delete(any());
        verifyNoMoreInteractions(employeeRepository, modelMapper);
    }
}
