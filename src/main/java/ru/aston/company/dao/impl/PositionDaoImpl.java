package ru.aston.company.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.aston.company.dao.PositionDao;
import ru.aston.company.model.entity.Position;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Optional.ofNullable;
import static org.springframework.util.CollectionUtils.firstElement;
import static ru.aston.company.dao.constant.DaoConstants.*;

@Repository
public class PositionDaoImpl implements PositionDao {
    private static final String SELECT_ALL_POSITIONS_HQL = """
            select position from Position position
            """;

    private static final String SELECT_POSITION_BY_ID_HQL = """
            select position from Position position
            left join fetch position.employees
            where position.id = :id
            """;

    private static final String SELECT_POSITION_BY_NAME_HQL = """
            select position from Position position
            left join fetch position.employees
            where position.name = :name
            """;


    @Override
    public List<Position> findAll(Session session) {
        return session.createQuery(SELECT_ALL_POSITIONS_HQL, Position.class)
                .getResultList();
    }

    @Override
    public Position findById(Session session, long id) {
        List<Position> positions = session.createQuery(SELECT_POSITION_BY_ID_HQL, Position.class)
                .setParameter(ID, id)
                .getResultList();

        return ofNullable(firstElement(positions))
                .orElseThrow(() -> new NoSuchElementException("Project with id " + id + " doesn't exist"));
    }

    @Override
    public Position findByName(Session session, String name) {
        List<Position> positions = session.createQuery(SELECT_POSITION_BY_NAME_HQL, Position.class)
                .setParameter(NAME, name)
                .getResultList();

        return ofNullable(firstElement(positions))
                .orElseThrow(() -> new NoSuchElementException("Position with name " + name + " doesn't exist"));
    }

    @Override
    public Position save(Session session, Position position) {
        session.persist(position);

        return position;
    }

    @Override
    public Position update(Session session, Position entity) {
        return session.merge(entity);
    }

    @Override
    public void delete(Session session, Position position) {
        session.remove(position);
    }
}
