package ru.aston.company.model.dto;

import java.util.List;

public record ProjectDetailDto(
        Long id,
        String name,
        List<EmployeeDto> employees
) {
}