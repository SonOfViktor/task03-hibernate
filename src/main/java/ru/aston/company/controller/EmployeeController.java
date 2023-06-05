package ru.aston.company.controller;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aston.company.model.dto.EmployeeDetailDto;
import ru.aston.company.model.dto.EmployeeDto;
import ru.aston.company.model.validation.group.ForCreate;
import ru.aston.company.service.EmployeeService;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeDetailDto findById(@PathVariable long id) {
        return employeeService.findById(id);
    }

    @GetMapping("/name/{name}")
    public List<EmployeeDetailDto> findByName(@PathVariable String name) {
        return employeeService.findAllByName(name);
    }

    @PostMapping
    public EmployeeDetailDto addEmployee(@Validated({ForCreate.class, Default.class})
                                         @RequestBody EmployeeDetailDto employee) {
        return employeeService.addEmployee(employee);
    }

    @PatchMapping("/{id}")
    public EmployeeDetailDto updateEmployee(@PathVariable long id,
                                            @Valid @RequestBody EmployeeDetailDto employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
    }
}