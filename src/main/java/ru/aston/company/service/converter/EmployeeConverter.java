package ru.aston.company.service.converter;

import org.hibernate.Session;
import ru.aston.company.dao.PositionDao;
import ru.aston.company.dao.ProjectDao;
import ru.aston.company.model.dto.EmployeeDetailDto;
import ru.aston.company.model.dto.EmployeeDto;
import ru.aston.company.model.entity.Employee;

public interface EmployeeConverter {
    EmployeeDto convertEmployeeToEmployeeDto(Employee employee);

    EmployeeDetailDto convertEmployeeToEmployeeDetailDto(Employee employee);

    Employee convertEmployeeDetailDtoToEmployee(EmployeeDetailDto employee, Session session,
                                                ProjectDao projectDao, PositionDao positionDao);
}
