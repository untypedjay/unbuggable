package io.untypedjay.unbuggable.logic;

import org.springframework.transaction.annotation.Transactional;
import io.untypedjay.unbuggable.dao.EmployeeDao;
import io.untypedjay.unbuggable.domain.Employee;

import java.util.List;

@Transactional()
public class WorkLogImpl1 implements WorkLogFacade {
  private EmployeeDao employeeDao;

  public void setEmployeeDao(EmployeeDao dao) {
    this.employeeDao = dao;
  }

  @Override
  public Employee syncEmployee(Employee employee) {
    return employeeDao.merge(employee);
  }

  @Override
  @Transactional(readOnly = true)
  public Employee findEmployeeById(Long id) {
    return employeeDao.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Employee> findAllEmployees() {
    return employeeDao.findAll();
  }
}
