package ru.aston.company.service;

import ru.aston.company.model.dto.EmployeeDetailDto;
import ru.aston.company.model.dto.EmployeeDto;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> findAll();

    EmployeeDetailDto findById(long id);

    List<EmployeeDetailDto> findAllByName(String name);

    EmployeeDetailDto addEmployee(EmployeeDetailDto employee);

    EmployeeDetailDto updateEmployee(long id, EmployeeDetailDto employee);

    void deleteEmployee(long id);
}
