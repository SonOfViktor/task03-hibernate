package ru.aston.company.dao;

import org.hibernate.Session;
import java.util.List;

public interface BaseDao<T> {
    List<T> findAll(Session session);

    T findById(Session session, long id);

    T save(Session session, T entity);

    T update(Session session, T entity);

    void delete(Session session, T entity);
}
