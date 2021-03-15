package io.untypedjay.dao;

import io.untypedjay.domain.Employee;
import io.untypedjay.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
  @Override
  public List<Employee> getAllEmployees() {
    EntityManager em = JpaUtil.getEntityManager();
    return em.createQuery("select e from Employee e", Employee.class).getResultList();
  }

  @Override
  public Employee getEmployee(Long employeeId) {
    // TODO
    return null;
  }

  @Override
  public void addEmployee(Employee employee) {
    // TODO
  }

  @Override
  public void updateEmployee(Employee employee) {
    // TODO
  }

  @Override
  public void deleteEmployee(Employee employee) {
    // TODO
  }

}
