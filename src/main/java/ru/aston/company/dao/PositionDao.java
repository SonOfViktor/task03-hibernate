package ru.aston.company.dao;

import org.hibernate.Session;
import ru.aston.company.model.entity.Position;

public interface PositionDao extends BaseDao<Position> {
    Position findByName(Session session, String name);
}
