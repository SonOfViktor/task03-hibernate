package ru.aston.company.controller;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aston.company.model.dto.PositionDetailDto;
import ru.aston.company.model.dto.PositionDto;
import ru.aston.company.model.validation.group.ForCreate;
import ru.aston.company.service.PositionService;
import java.util.List;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {
    private final PositionService positionService;

    @GetMapping
    public List<PositionDto> findAll() {
        return positionService.findAll();
    }

    @GetMapping("/{id}")
    public PositionDetailDto findById(@PathVariable long id) {
        return positionService.findById(id);
    }

    @GetMapping("/name/{name}")
    public PositionDetailDto findByName(@PathVariable String name) {
        return positionService.findByName(name);
    }

    @PostMapping
    public PositionDto addPosition(@Validated({ForCreate.class, Default.class})
                                       @RequestBody PositionDto position) {
        return positionService.addPosition(position);
    }

    @PatchMapping("/{id}")
    public PositionDetailDto updatePosition(@PathVariable long id,
                                            @Valid @RequestBody PositionDto position) {
        return positionService.updatePosition(id, position);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePosition(@PathVariable long id) {
        positionService.deletePosition(id);
    }
}