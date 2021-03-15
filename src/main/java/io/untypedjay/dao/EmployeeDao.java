package io.untypedjay.dao;

import io.untypedjay.domain.Employee;

import java.util.List;

public interface EmployeeDao {
  public List<Employee> getAllEmployees();
  public Employee getEmployee(Long employeeId);
  public void addEmployee(Employee employee);
  public void updateEmployee(Employee employee);
  public void deleteEmployee(Employee employee);
}
