package ru.aston.company.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.aston.company.dao.ProjectDao;
import ru.aston.company.model.dto.ProjectDetailDto;
import ru.aston.company.model.dto.ProjectDto;
import ru.aston.company.model.entity.Project;
import ru.aston.company.service.ProjectService;
import ru.aston.company.service.converter.ProjectConverter;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final SessionFactory sessionFactory;
    private final ProjectDao projectDao;
    private final ProjectConverter projectConverter;

    public List<ProjectDto> findAll() {
        Session session = sessionFactory.openSession();
        List<ProjectDto> projectDtoList;

        try (session) {
            session.beginTransaction();

            List<Project> projects = projectDao.findAll(session);
            projectDtoList = projects.stream()
                    .map(projectConverter::convertProjectToProjectDto)
                    .toList();

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

        return projectDtoList;
    }

    public ProjectDetailDto findById(long id) {
        Session session = sessionFactory.openSession();
        ProjectDetailDto projectDetailDto;

        try (session) {
            session.beginTransaction();

            projectDetailDto = projectConverter.convertProjectToProjectDetailDto(projectDao.findById(session, id));

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

        return projectDetailDto;
    }

    public ProjectDetailDto findByName(String name) {
        Session session = sessionFactory.openSession();
        ProjectDetailDto projectDetailDto;

        try (session) {
            session.beginTransaction();

            projectDetailDto = projectConverter.convertProjectToProjectDetailDto(projectDao.findByName(session, name));

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

        return projectDetailDto;
    }

    @Override
    public ProjectDto addProject(ProjectDto project) {
        Session session = sessionFactory.openSession();
        ProjectDto addedProjectDto;

        try (session) {
            session.beginTransaction();

            Project addedProject = Project.builder()
                    .name(project.name())
                    .build();
            projectDao.save(session, addedProject);
            addedProjectDto = projectConverter.convertProjectToProjectDto(addedProject);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

        return addedProjectDto;
    }

    @Override
    public ProjectDetailDto updateProject(long id, ProjectDto project) {
        Session session = sessionFactory.openSession();
        ProjectDetailDto updatedProjectDto;

        try (session) {
            session.beginTransaction();

            Project oldProject = projectDao.findById(session, id);
            oldProject.setName(hasText(project.name()) ? project.name() : oldProject.getName());
            updatedProjectDto = projectConverter.convertProjectToProjectDetailDto(oldProject);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

        return updatedProjectDto;
    }

    @Override
    public void deleteProject(long id) {
        Session session = sessionFactory.openSession();

        try (session) {
            session.beginTransaction();

            Project project = projectDao.findById(session, id);
            projectDao.delete(session, project);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }
    }
}