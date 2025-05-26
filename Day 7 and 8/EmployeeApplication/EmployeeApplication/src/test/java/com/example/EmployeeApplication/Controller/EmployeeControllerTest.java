package com.example.EmployeeApplication.Controller;

import com.example.EmployeeApplication.DTO.EmployeeDto;
import com.example.EmployeeApplication.Service.IEmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private IEmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private EmployeeDto employeeDto;
    private List<EmployeeDto> employeeList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        objectMapper = new ObjectMapper();

        employeeDto = new EmployeeDto();
        employeeDto.setName("John Doe");
        employeeDto.setEmail("john.doe@example.com");
        employeeDto.setDepartment("IT");
        employeeDto.setSalary(50000.0f);

        EmployeeDto employee2 = new EmployeeDto();
        employee2.setName("Jane Smith");
        employee2.setEmail("jane.smith@example.com");
        employee2.setDepartment("HR");
        employee2.setSalary(45000.0f);

        employeeList = Arrays.asList(employeeDto, employee2);
    }

    @Test
    void addEmployee_ShouldReturnCreatedStatus_WhenEmployeeIsAddedSuccessfully() throws Exception {
        when(employeeService.addEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);

        mockMvc.perform(post("/api/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.department").value("IT"))
                .andExpect(jsonPath("$.salary").value(50000.0));

        verify(employeeService, times(1)).addEmployee(any(EmployeeDto.class));
    }

    @Test
    void addEmployee_ShouldHandleServiceException() throws Exception {
        when(employeeService.addEmployee(any(EmployeeDto.class))).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(post("/api/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isInternalServerError());

        verify(employeeService, times(1)).addEmployee(any(EmployeeDto.class));
    }

    @Test
    void getEmployees_ShouldReturnAllEmployees_WhenEmployeesExist() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(employeeList);

        mockMvc.perform(get("/api/employee/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"))
                .andExpect(jsonPath("$[1].email").value("jane.smith@example.com"));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void getEmployees_ShouldReturnEmptyList_WhenNoEmployeesExist() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/employee/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void addEmployeesByDoc_ShouldReturnCreatedStatus_WhenFileIsUploadedSuccessfully() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "employees.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "test content".getBytes());
        String successMessage = "2 employees added successfully";
        when(employeeService.addEmployeesUsingExcel(any(MultipartFile.class))).thenReturn(successMessage);

        mockMvc.perform(multipart("/api/employee/upload").file(file))
                .andExpect(status().isCreated())
                .andExpect(content().string(successMessage));

        verify(employeeService, times(1)).addEmployeesUsingExcel(any(MultipartFile.class));
    }

    @Test
    void addEmployeesByDoc_ShouldHandleIOException() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "employees.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "test content".getBytes());
        when(employeeService.addEmployeesUsingExcel(any(MultipartFile.class))).thenThrow(new IOException("File processing error"));

        mockMvc.perform(multipart("/api/employee/upload").file(file))
                .andExpect(status().isInternalServerError());

        verify(employeeService, times(1)).addEmployeesUsingExcel(any(MultipartFile.class));
    }

    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee_WhenUpdateIsSuccessful() throws Exception {
        int employeeId = 1;
        EmployeeDto updatedEmployee = new EmployeeDto();
        updatedEmployee.setName("John Doe Updated");
        updatedEmployee.setEmail("john.updated@example.com");
        updatedEmployee.setDepartment("IT");
        updatedEmployee.setSalary(55000.0f);

        when(employeeService.updateEmployee(eq(employeeId), any(EmployeeDto.class))).thenReturn(updatedEmployee);

        mockMvc.perform(put("/api/employee/update")
                        .param("id", String.valueOf(employeeId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"));

        verify(employeeService, times(1)).updateEmployee(eq(employeeId), any(EmployeeDto.class));
    }

    @Test
    void updateEmployee_ShouldHandleNonExistentEmployee() throws Exception {
        int employeeId = 999;
        when(employeeService.updateEmployee(eq(employeeId), any(EmployeeDto.class)))
                .thenThrow(new RuntimeException("Employee not found"));

        mockMvc.perform(put("/api/employee/update")
                        .param("id", String.valueOf(employeeId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isInternalServerError());

        verify(employeeService, times(1)).updateEmployee(eq(employeeId), any(EmployeeDto.class));
    }

    @Test
    void deleteEmployee_ShouldReturnSuccessMessage_WhenDeleteIsSuccessful() throws Exception {
        int employeeId = 1;
        String successMessage = "Employee deleted successfully";
        when(employeeService.deleteEmployee(employeeId)).thenReturn(successMessage);

        mockMvc.perform(delete("/api/employee/delete")
                        .param("id", String.valueOf(employeeId)))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));

        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }

    @Test
    void deleteEmployee_ShouldHandleNonExistentEmployee() throws Exception {
        int employeeId = 999;
        when(employeeService.deleteEmployee(employeeId)).thenThrow(new RuntimeException("Employee not found"));

        mockMvc.perform(delete("/api/employee/delete")
                        .param("id", String.valueOf(employeeId)))
                .andExpect(status().isInternalServerError());

        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }

    @Test
    void addEmployee_ShouldReturnBadRequest_WhenInvalidRequestBody() throws Exception {
        mockMvc.perform(post("/api/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid json"))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).addEmployee(any(EmployeeDto.class));
    }

    @Test
    void updateEmployee_ShouldReturnBadRequest_WhenMissingIdParameter() throws Exception {
        mockMvc.perform(put("/api/employee/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).updateEmployee(anyInt(), any(EmployeeDto.class));
    }

    @Test
    void deleteEmployee_ShouldReturnBadRequest_WhenMissingIdParameter() throws Exception {
        mockMvc.perform(delete("/api/employee/delete"))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).deleteEmployee(anyInt());
    }

    @Test
    void addEmployeesByDoc_ShouldReturnBadRequest_WhenNoFileProvided() throws Exception {
        mockMvc.perform(multipart("/api/employee/upload"))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).addEmployeesUsingExcel(any(MultipartFile.class));
    }
}
