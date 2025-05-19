package com.example.EmployeeApplication.Controller;

import com.example.EmployeeApplication.DTO.EmployeeDto;
import com.example.EmployeeApplication.Service.IEmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void testAddEmployee() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto("Vaibhav","vaibhav@gmail.com","HR",58796f);
        when(employeeService.addEmployee(any())).thenReturn(employeeDto);
        mockMvc.perform(post("/api/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Vaibhav"));
    }
    @Test
    void testGetEmployees() throws Exception{
        List<EmployeeDto> employeeDtos = Arrays
                .asList(new EmployeeDto("Aditya","aditya@gmail.com","IT",63000f));
        when(employeeService.getAllEmployees()).thenReturn(employeeDtos);

        mockMvc.perform(get("/api/employee/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].department").value("IT"));

    }
    @Test
    void testDeleteEmployee() throws Exception {
        when(employeeService.deleteEmployee(1)).thenReturn("Employee with ID 1 deleted successfully.");

        mockMvc.perform(delete("/api/employee/delete?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee with ID 1 deleted successfully."));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        int id = 1;
        EmployeeDto dto = new EmployeeDto("Updated Name", "updated@example.com", "IT", 55000f);

        Mockito.when(employeeService.updateEmployee(anyInt(), any(EmployeeDto.class)))
                .thenReturn(dto);

        mockMvc.perform(put("/api/employee/update")
                        .param("id", String.valueOf(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.department").value("IT"))
                .andExpect(jsonPath("$.salary").value(55000f));
    }

}
