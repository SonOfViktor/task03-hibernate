package ru.aston.company.service.converter;

import ru.aston.company.model.dto.PositionDetailDto;
import ru.aston.company.model.dto.PositionDto;
import ru.aston.company.model.entity.Position;

public interface PositionConverter {
    PositionDto convertPositionToPositionDto(Position position);

    PositionDetailDto convertPositionToPositionDetailDto(Position position);
}
