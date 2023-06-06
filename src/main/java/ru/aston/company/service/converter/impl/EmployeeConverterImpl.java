package ru.aston.company.service.converter.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import ru.aston.company.dao.PositionDao;
import ru.aston.company.dao.ProjectDao;
import ru.aston.company.model.dto.EmployeeDetailDto;
import ru.aston.company.model.dto.EmployeeDto;
import ru.aston.company.model.entity.Employee;
import ru.aston.company.model.entity.Project;
import ru.aston.company.service.converter.EmployeeConverter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class EmployeeConverterImpl implements EmployeeConverter {
    @Override
    public EmployeeDto convertEmployeeToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName()
        );
    }

    @Override
    public EmployeeDetailDto convertEmployeeToEmployeeDetailDto(Employee employee) {
        return new EmployeeDetailDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPosition().getName(),
                employee.getProjects()
                        .stream()
                        .map(Project::getName)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public Employee convertEmployeeDetailDtoToEmployee(EmployeeDetailDto employee, Session session,
                                                       ProjectDao projectDao, PositionDao positionDao) {
        return Employee.builder()
                .firstName(employee.firstName())
                .lastName(employee.lastName())
                .position(positionDao.findByName(session, employee.position()))
                .projects(ofNullable(employee.projects())
                        .map(names -> names.stream()
                                .map(name -> projectDao.findByName(session, name))
                                .toList())
                        .orElseGet(ArrayList::new))
                .build();
    }
}