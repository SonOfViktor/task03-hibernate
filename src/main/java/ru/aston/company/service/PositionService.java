package ru.aston.company.service;

import ru.aston.company.model.dto.PositionDetailDto;
import ru.aston.company.model.dto.PositionDto;
import java.util.List;

public interface PositionService {
    List<PositionDto> findAll();

    PositionDetailDto findById(long id);

    PositionDetailDto findByName(String name);

    PositionDto addPosition(PositionDto position);

    PositionDetailDto updatePosition(long id, PositionDto position);

    void deletePosition(long id);
}
