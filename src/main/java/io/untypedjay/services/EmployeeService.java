package io.untypedjay.services;

import io.untypedjay.domain.Employee;

import java.util.List;

public interface EmployeeService {
  List<Employee> getAll();
}
