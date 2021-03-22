package io.untypedjay.unbuggable.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "projects", fetch = FetchType.LAZY)
  private Set<Employee> employees = new HashSet<>();

  public Project() {
  }

  public Project(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(Set<Employee> employees) {
    this.employees = employees;
  }
}
