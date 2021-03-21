package io.untypedjay.unbuggable.logic;

import java.util.List;
import io.untypedjay.unbuggable.domain.Employee;

public interface WorkLogFacade {
  public Employee       syncEmployee(Employee employee);
  public Employee       findEmployeeById(Long id);
  public List<Employee> findAllEmployees();
}