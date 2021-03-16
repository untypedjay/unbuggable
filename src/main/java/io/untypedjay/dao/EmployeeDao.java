package io.untypedjay.dao;

import io.untypedjay.domain.Employee;

import java.util.List;

public interface EmployeeDao {
  List<Employee> getAllEmployees();
  Employee getEmployee(Long employeeId);
  void addEmployee(Employee employee);
  void updateEmployee(Employee employee);
  void deleteEmployee(Employee employee);
}
