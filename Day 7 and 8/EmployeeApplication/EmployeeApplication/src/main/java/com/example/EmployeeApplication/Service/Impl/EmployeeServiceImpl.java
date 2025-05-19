package com.example.EmployeeApplication.Service.Impl;

import com.example.EmployeeApplication.DTO.EmployeeDto;
import com.example.EmployeeApplication.Entity.Employee;
import com.example.EmployeeApplication.Exception.*;
import com.example.EmployeeApplication.Repository.EmployeeRepository;
import com.example.EmployeeApplication.Service.IEmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        Employee saved = employeeRepository.save(employee);
        return modelMapper.map(saved, EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream().
                map(e-> modelMapper.map(e,EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(int id, EmployeeDto employeeDto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        existing.setName(employeeDto.getName());
        existing.setEmail(employeeDto.getEmail());
        existing.setDepartment(employeeDto.getDepartment());

        Employee updated = employeeRepository.save(existing);
        return modelMapper.map(updated, EmployeeDto.class);
    }

    @Override
    public String deleteEmployee(int id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employeeRepository.delete(existing);
        return "Employee with ID " + id + " deleted successfully.";
    }

    @Override
    public String addEmployeesUsingExcel(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new InvalidFileException("Uploaded file is empty.");
        }

        String filename = multipartFile.getOriginalFilename();
        List<EmployeeDto> employeeDtos;

        if (filename != null && filename.endsWith(".xlsx")) {
            employeeDtos = parseExcel(multipartFile.getInputStream());
        } else if (filename != null && filename.endsWith(".csv")) {
            employeeDtos = parseCsv(multipartFile.getInputStream());
        } else {
            throw new InvalidFileException("Unsupported file format. Only .xlsx and .csv are allowed.");
        }

        List<Employee> employees = employeeDtos.stream()
                .map(dto -> modelMapper.map(dto, Employee.class))
                .collect(Collectors.toList());

        employeeRepository.saveAll(employees);
        return employees.size() + " employees added successfully from " + filename;

    }

    private List<EmployeeDto> parseCsv(InputStream is) throws IOException {
        List<EmployeeDto> list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // skip header
                    isFirstLine = false;
                    continue;
                }

                String[] fields = line.split(",");

                if (fields.length < 4) continue; // skip if missing fields

                EmployeeDto dto = new EmployeeDto();
                dto.setName(fields[0].trim());
                dto.setEmail(fields[1].trim());
                dto.setDepartment(fields[2].trim());
                dto.setSalary(Float.parseFloat(fields[3].trim()));

                list.add(dto);
            }
        }

        return list;
    }

    private List<EmployeeDto> parseExcel(InputStream is) {
        List<EmployeeDto> list = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                EmployeeDto dto = new EmployeeDto();
                dto.setName(row.getCell(0).getStringCellValue());
                dto.setEmail(row.getCell(1).getStringCellValue());
                dto.setDepartment(row.getCell(2).getStringCellValue());
                dto.setSalary((float) row.getCell(3).getNumericCellValue());
                list.add(dto);
            }
        } catch (IOException | NullPointerException | IllegalStateException e) {
            throw new InvalidFileException("Failed to parse Excel file: " + e.getMessage());
        }
        return list;
    }
}
