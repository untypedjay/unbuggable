package io.untypedjay.services;

import io.untypedjay.dao.EmployeeDao;
import io.untypedjay.dao.ProjectDao;
import io.untypedjay.domain.Employee;
import io.untypedjay.domain.Project;
import io.untypedjay.util.PersistenceManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
  private static PersistenceManager persistenceManager = PersistenceManager.getInstance();

  @Override
  public List<Employee> getAll() {
    List<Employee> employees;
    try {
      persistenceManager.beginTransaction();
      EmployeeDao employeeDao = persistenceManager.getEmployeeDao();
      employees = employeeDao.getAllEmployees();
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }

    return employees;
  }

  @Override
  public void add(String firstName, String lastName, String unparsedDateOfBirth) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
      LocalDate dob = LocalDate.parse(unparsedDateOfBirth, formatter);
      persistenceManager.beginTransaction();
      EmployeeDao employeeDao = persistenceManager.getEmployeeDao();
      employeeDao.addEmployee(new Employee(firstName, lastName, dob));
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }
  }

  @Override
  public void remove(Long employeeId) {
    try {
      persistenceManager.beginTransaction();
      EmployeeDao employeeDao = persistenceManager.getEmployeeDao();
      Employee employee = employeeDao.getEmployee(employeeId);
      employeeDao.deleteEmployee(employee);
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }
  }
}
