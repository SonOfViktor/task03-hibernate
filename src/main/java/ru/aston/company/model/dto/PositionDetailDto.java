package ru.aston.company.model.dto;

import java.util.List;

public record PositionDetailDto(
        Long id,
        String name,
        List<EmployeeDto> employees
) {
}