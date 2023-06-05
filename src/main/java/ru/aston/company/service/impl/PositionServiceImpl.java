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

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
    private final SessionFactory sessionFactory;
    private final PositionDao positionDao;
    private final PositionConverter positionConverter;

    @Override
    public List<PositionDto> findAll() {
        Session session = sessionFactory.openSession();
        List<PositionDto> positionDtoList;

        try (session) {
            session.beginTransaction();

            List<Position> positions = positionDao.findAll(session);
            positionDtoList = positions.stream()
                    .map(positionConverter::convertPositionToPositionDto)
                    .toList();

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

        return positionDtoList;
    }

    @Override
    public PositionDetailDto findById(long id) {
        Session session = sessionFactory.openSession();
        PositionDetailDto positionDetailDto;

        try (session) {
            session.beginTransaction();

            positionDetailDto = positionConverter.convertPositionToPositionDetailDto(positionDao.findById(session, id));

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

        return positionDetailDto;
    }

    @Override
    public PositionDetailDto findByName(String name) {
        Session session = sessionFactory.openSession();
        PositionDetailDto positionDetailDto;

        try (session) {
            session.beginTransaction();

            positionDetailDto = positionConverter
                    .convertPositionToPositionDetailDto(positionDao.findByName(session, name));

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

        return positionDetailDto;
    }

    @Override
    public PositionDto addPosition(PositionDto position) {
        Session session = sessionFactory.openSession();
        PositionDto addedPositionDto;

        try (session) {
            session.beginTransaction();

            Position addedPosition = Position.builder()
                    .name(position.name())
                    .build();
            positionDao.save(session, addedPosition);
            addedPositionDto = positionConverter.convertPositionToPositionDto(addedPosition);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

        return addedPositionDto;
    }

    @Override
    public PositionDetailDto updatePosition(long id, PositionDto position) {
        Session session = sessionFactory.openSession();
        PositionDetailDto updatedPositionDto;

        try (session) {
            session.beginTransaction();

            Position oldPosition = positionDao.findById(session, id);
            oldPosition.setName(hasText(position.name()) ? position.name() : oldPosition.getName());
            updatedPositionDto = positionConverter.convertPositionToPositionDetailDto(oldPosition);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

        return updatedPositionDto;
    }

    @Override
    public void deletePosition(long id) {
        Session session = sessionFactory.openSession();

        try (session) {
            session.beginTransaction();

            Position position = positionDao.findById(session, id);
            positionDao.delete(session, position);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw ex;
        }

    }
}