package ru.aston.company.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.aston.company.dao.EmployeeDao;
import ru.aston.company.model.entity.Employee;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Optional.ofNullable;
import static org.springframework.util.CollectionUtils.firstElement;
import static ru.aston.company.dao.constant.DaoConstants.*;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    private static final String SELECT_ALL_EMPLOYEES_HQL = """
            select employee from Employee employee
            """;

    private static final String SELECT_EMPLOYEE_BY_ID_HQL = """
            select employee from Employee employee
            left join fetch employee.projects
            join fetch employee.position
            where employee.id = :id
            """;

    private static final String SELECT_EMPLOYEE_BY_NAME_HQL = """
            select employee from Employee employee
            join fetch employee.projects
            join fetch employee.position
            where employee.firstName = :name
            """;

    @Override
    public List<Employee> findAll(Session session) {
        return session.createQuery(SELECT_ALL_EMPLOYEES_HQL, Employee.class)
                .getResultList();
    }

    @Override
    public Employee findById(Session session, long id) {
        List<Employee> employees = session.createQuery(SELECT_EMPLOYEE_BY_ID_HQL, Employee.class)
                .setParameter(ID, id)
                .getResultList();

        return ofNullable(firstElement(employees))
                .orElseThrow(() -> new NoSuchElementException("Project with id " + id + " doesn't exist"));
    }

    @Override
    public List<Employee> findByName(Session session, String name) {
        return session.createQuery(SELECT_EMPLOYEE_BY_NAME_HQL, Employee.class)
                .setParameter(NAME, name)
                .getResultList();
    }

    @Override
    public Employee save(Session session, Employee employee) {
        session.persist(employee);

        return employee;
    }

    @Override
    public Employee update(Session session, Employee employee) {
        return session.merge(employee);
    }

    @Override
    public void delete(Session session, Employee employee) {
        session.remove(employee);
    }
}
