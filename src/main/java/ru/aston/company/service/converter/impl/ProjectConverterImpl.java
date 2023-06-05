package ru.aston.company.service.converter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.aston.company.model.dto.ProjectDetailDto;
import ru.aston.company.model.dto.ProjectDto;
import ru.aston.company.model.entity.Project;
import ru.aston.company.service.converter.EmployeeConverter;
import ru.aston.company.service.converter.ProjectConverter;

@Component
@RequiredArgsConstructor
public class ProjectConverterImpl implements ProjectConverter {
    private final EmployeeConverter employeeConverter;

    @Override
    public ProjectDto convertProjectToProjectDto(Project project) {
        return new ProjectDto(project.getId(), project.getName());
    }

    @Override
    public ProjectDetailDto convertProjectToProjectDetailDto(Project project) {
        return new ProjectDetailDto(
                project.getId(),
                project.getName(),
                project.getEmployees()
                        .stream()
                        .map(employeeConverter::convertEmployeeToEmployeeDto)
                        .toList()
        );
    }
}
