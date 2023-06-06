package ru.aston.company.service.converter;

import ru.aston.company.model.dto.ProjectDetailDto;
import ru.aston.company.model.dto.ProjectDto;
import ru.aston.company.model.entity.Project;

public interface ProjectConverter {
    ProjectDto convertProjectToProjectDto(Project project);

    ProjectDetailDto convertProjectToProjectDetailDto(Project project);
}
