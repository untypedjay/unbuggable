package io.untypedjay.unbuggable.dao.jpa;

import io.untypedjay.unbuggable.dao.EmployeeDao;
import org.springframework.stereotype.Repository;
import io.untypedjay.unbuggable.domain.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EmployeeDaoJpa implements EmployeeDao {
  @PersistenceContext
  private EntityManager em;

  @Override
  public Employee findById(Long id) {
    return em.find(Employee.class, id);
  }

  @Override
  public List<Employee> findAll() {
    return em.createQuery("select e from Employee e", Employee.class).getResultList();
  }

  @Override
  public void insert(Employee employee) {
    em.persist(employee);
  }

  @Override
  public Employee merge(Employee employee) {
    return em.merge(employee);
  }
}
