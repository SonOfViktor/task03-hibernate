package ru.aston.company.service.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aston.company.model.dto.PositionDetailDto;
import ru.aston.company.model.dto.PositionDto;
import ru.aston.company.model.entity.Position;
import ru.aston.company.service.converter.EmployeeConverter;
import ru.aston.company.service.converter.PositionConverter;

@Component
@RequiredArgsConstructor
public class PositionConverterImpl implements PositionConverter {
    private final EmployeeConverter employeeConverter;

    @Override
    public PositionDto convertPositionToPositionDto(Position position) {
        return new PositionDto(position.getId(), position.getName());
    }

    @Override
    public PositionDetailDto convertPositionToPositionDetailDto(Position position) {
        return new PositionDetailDto(
                position.getId(),
                position.getName(),
                position.getEmployees()
                        .stream()
                        .map(employeeConverter::convertEmployeeToEmployeeDto)
                        .toList()
        );
    }
}