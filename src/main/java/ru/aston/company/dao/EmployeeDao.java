package ru.aston.company.dao;

import org.hibernate.Session;
import ru.aston.company.model.entity.Employee;
import java.util.List;

public interface EmployeeDao extends BaseDao<Employee> {
    List<Employee> findByName(Session session, String name);
}