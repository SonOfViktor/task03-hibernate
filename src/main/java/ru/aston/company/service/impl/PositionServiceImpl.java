package ru.aston.company.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.aston.company.dao.PositionDao;
import ru.aston.company.model.dto.PositionDetailDto;
import ru.aston.company.model.dto.PositionDto;
import ru.aston.company.model.entity.Position;
import ru.aston.company.service.PositionService;
import ru.aston.company.service.converter.PositionConverter;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final SessionFactory sessionFactory;
    private final PositionDao positionDao;
    private final PositionConverter positionConverter;

    @Override
    public List<PositionDto> findAll() {
        List<PositionDto> positionDtoList;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Position> positions = positionDao.findAll(session);
            positionDtoList = positions.stream()
                    .map(positionConverter::convertPositionToPositionDto)
                    .toList();

            session.getTransaction().commit();
        }

        return positionDtoList;
    }

    @Override
    public PositionDetailDto findById(long id) {
        PositionDetailDto positionDetailDto;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            positionDetailDto = positionConverter.convertPositionToPositionDetailDto(positionDao.findById(session, id));

            session.getTransaction().commit();
        }

        return positionDetailDto;
    }

    @Override
    public PositionDetailDto findByName(String name) {
        PositionDetailDto positionDetailDto;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            positionDetailDto = positionConverter
                    .convertPositionToPositionDetailDto(positionDao.findByName(session, name));

            session.getTransaction().commit();
        }

        return positionDetailDto;
    }

    @Override
    public PositionDto addPosition(PositionDto position) {
        Session session = null;
        PositionDto addedPositionDto;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Position addedPosition = Position.builder()
                    .name(position.name())
                    .build();
            positionDao.save(session, addedPosition);
            addedPositionDto = positionConverter.convertPositionToPositionDto(addedPosition);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ofNullable(session).ifPresent(s -> s.getTransaction().rollback());
            throw ex;
        } finally {
            ofNullable(session).ifPresent(Session::close);
        }

        return addedPositionDto;
    }

    @Override
    public PositionDetailDto updatePosition(long id, PositionDto position) {
        Session session = null;
        PositionDetailDto updatedPositionDto;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Position oldPosition = positionDao.findById(session, id);
            oldPosition.setName(hasText(position.name()) ? position.name() : oldPosition.getName());
            updatedPositionDto = positionConverter.convertPositionToPositionDetailDto(oldPosition);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ofNullable(session).ifPresent(s -> s.getTransaction().rollback());
            throw ex;
        } finally {
            ofNullable(session).ifPresent(Session::close);
        }

        return updatedPositionDto;
    }

    @Override
    public void deletePosition(long id) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Position position = positionDao.findById(session, id);
            positionDao.delete(session, position);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ofNullable(session).ifPresent(s -> s.getTransaction().rollback());
            throw ex;
        } finally {
            ofNullable(session).ifPresent(Session::close);
        }
    }
}