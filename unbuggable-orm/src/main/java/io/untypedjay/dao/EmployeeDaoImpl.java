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
    EntityManager em = JpaUtil.getEntityManager();
    return em.find(Employee.class, employeeId);
  }

  @Override
  public void addEmployee(Employee employee) {
    EntityManager em = JpaUtil.getEntityManager();
    em.persist(employee);
  }

  @Override
  public void updateEmployee(Employee employee) {
    EntityManager em = JpaUtil.getEntityManager();
    em.merge(employee);
  }

  @Override
  public void deleteEmployee(Employee employee) {
    EntityManager em = JpaUtil.getEntityManager();
    em.remove(employee);
  }
}
