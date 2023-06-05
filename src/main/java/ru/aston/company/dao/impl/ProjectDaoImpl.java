package ru.aston.company.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.aston.company.dao.ProjectDao;
import ru.aston.company.model.entity.Project;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Optional.ofNullable;
import static org.springframework.util.CollectionUtils.firstElement;
import static ru.aston.company.dao.constant.DaoConstants.*;

@Repository
public class ProjectDaoImpl implements ProjectDao {
    private static final String SELECT_ALL_PROJECTS_HQL = """
            select project from Project project
            """;

    private static final String SELECT_PROJECT_BY_ID_HQL = """
            select project from Project project
            left join fetch project.employees
            where project.id = :id
            """;

    private static final String SELECT_PROJECT_BY_NAME_HQL = """
            select project from Project project
            left join fetch project.employees
            where project.name = :name
            """;

    @Override
    public List<Project> findAll(Session session) {
        return session.createQuery(SELECT_ALL_PROJECTS_HQL, Project.class)
                .getResultList();
    }

    @Override
    public Project findById(Session session, long id) {
        List<Project> projects = session.createQuery(SELECT_PROJECT_BY_ID_HQL, Project.class)
                .setParameter(ID, id)
                .getResultList();

        return ofNullable(firstElement(projects))
                .orElseThrow(() -> new NoSuchElementException("Project with id " + id + " doesn't exist"));
    }

    @Override
    public Project findByName(Session session, String name) {
        List<Project> projects = session.createQuery(SELECT_PROJECT_BY_NAME_HQL, Project.class)
                .setParameter(NAME, name)
                .getResultList();

        return ofNullable(CollectionUtils.firstElement(projects))
                .orElseThrow(() -> new NoSuchElementException("Project with name " + name + " doesn't exist"));
    }

    @Override
    public Project save(Session session, Project project) {
        session.persist(project);

        return project;
    }

    @Override
    public Project update(Session session, Project project) {
        return session.merge(project);
    }

    @Override
    public void delete(Session session, Project project) {
        session.remove(project);
    }
}