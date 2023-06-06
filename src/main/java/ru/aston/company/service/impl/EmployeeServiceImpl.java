package ru.aston.company.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import ru.aston.company.dao.EmployeeDao;
import ru.aston.company.dao.PositionDao;
import ru.aston.company.dao.ProjectDao;
import ru.aston.company.model.dto.EmployeeDetailDto;
import ru.aston.company.model.dto.EmployeeDto;
import ru.aston.company.model.entity.Employee;
import ru.aston.company.service.EmployeeService;
import ru.aston.company.service.converter.EmployeeConverter;
import java.util.List;

import static java.util.Optional.*;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final SessionFactory sessionFactory;
    private final EmployeeDao employeeDao;
    private final ProjectDao projectDao;
    private final PositionDao positionDao;
    private final EmployeeConverter employeeConverter;

    @Override
    public List<EmployeeDto> findAll() {
        Session session = sessionFactory.openSession();
        List<EmployeeDto> employeeDtoList;

        try (session) {
            session.beginTransaction();

            List<Employee> employees = employeeDao.findAll(session);
            employeeDtoList = employees.stream()
                    .map(employeeConverter::convertEmployeeToEmployeeDto)
                    .toList();

            session.getTransaction().commit();
        }

        return employeeDtoList;
    }

    @Override
    public EmployeeDetailDto findById(long id) {
        EmployeeDetailDto employeeDetailDto;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            employeeDetailDto = employeeConverter.convertEmployeeToEmployeeDetailDto(employeeDao.findById(session, id));

            session.getTransaction().commit();
        }

        return employeeDetailDto;
    }

    @Override
    public List<EmployeeDetailDto> findAllByName(String name) {
        List<EmployeeDetailDto> employeeDtoList;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            employeeDtoList = employeeDao.findByName(session, name)
                    .stream()
                    .map(employeeConverter::convertEmployeeToEmployeeDetailDto)
                    .toList();

            session.getTransaction().commit();
        }

        return employeeDtoList;
    }

    @Override
    public EmployeeDetailDto addEmployee(EmployeeDetailDto employee) {
        Session session = null;
        EmployeeDetailDto addedEmployeeDto;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Employee addedEmployee = employeeConverter
                    .convertEmployeeDetailDtoToEmployee(employee, session, projectDao, positionDao);
            employeeDao.save(session, addedEmployee);
            addedEmployeeDto = employeeConverter.convertEmployeeToEmployeeDetailDto(addedEmployee);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ofNullable(session).ifPresent(s -> s.getTransaction().rollback());
            throw ex;
        } finally {
            ofNullable(session).ifPresent(Session::close);
        }

        return addedEmployeeDto;
    }

    @Override
    public EmployeeDetailDto updateEmployee(long id, EmployeeDetailDto employee) {
        Session session = null;
        EmployeeDetailDto updatedEmployeeDto;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Employee updatedEmployee = employeeDao.findById(session, id);
            fillUpdatedEmployeeWithNewData(updatedEmployee, employee, session);
            updatedEmployeeDto = employeeConverter.convertEmployeeToEmployeeDetailDto(updatedEmployee);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ofNullable(session).ifPresent(s -> s.getTransaction().rollback());
            throw ex;
        } finally {
            ofNullable(session).ifPresent(Session::close);
        }

        return updatedEmployeeDto;
    }

    @Override
    public void deleteEmployee(long id) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            Employee employee = employeeDao.findById(session, id);
            employeeDao.delete(session, employee);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ofNullable(session).ifPresent(s -> s.getTransaction().rollback());
            throw ex;
        } finally {
            ofNullable(session).ifPresent(Session::close);
        }
    }

    private void fillUpdatedEmployeeWithNewData(Employee updatedEmployee, EmployeeDetailDto employee, Session session) {
        updatedEmployee.setFirstName(hasText(employee.firstName()) ? employee.firstName() : updatedEmployee.getFirstName());
        updatedEmployee.setLastName(hasText(employee.lastName()) ? employee.lastName() : updatedEmployee.getLastName());

        updatedEmployee.setPosition(hasText(employee.position())
                ? positionDao.findByName(session, employee.position())
                : updatedEmployee.getPosition());

        updatedEmployee.setProjects(employee.projects() != null
                ? employee.projects()
                    .stream()
                    .map(name -> projectDao.findByName(session, name))
                    .toList()
                : updatedEmployee.getProjects());
    }
}
