package ru.aston.company.dao;

import org.hibernate.Session;
import ru.aston.company.model.entity.Project;

public interface ProjectDao extends BaseDao<Project> {
    Project findByName(Session session, String name);
}
