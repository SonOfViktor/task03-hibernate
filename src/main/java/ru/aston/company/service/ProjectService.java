package ru.aston.company.service;

import ru.aston.company.model.dto.ProjectDetailDto;
import ru.aston.company.model.dto.ProjectDto;
import java.util.List;

public interface ProjectService {
    List<ProjectDto> findAll();

    ProjectDetailDto findById(long id);

    ProjectDetailDto findByName(String name);

    ProjectDto addProject(ProjectDto project);

    ProjectDetailDto updateProject(long id, ProjectDto project);

    void deleteProject(long id);
}
