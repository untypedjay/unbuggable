package io.untypedjay.services;

import io.untypedjay.domain.Employee;

import java.util.List;

public interface EmployeeService {
  List<Employee> getAll();
  void add(String firstName, String lastName, String unparsedDateOfBirth);
  void remove(Long employeeId);
}
