package io.untypedjay.dao;

import io.untypedjay.domain.Employee;
import io.untypedjay.domain.Project;
import io.untypedjay.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao {
  @Override
  public List<Project> getAllProjects() {
    EntityManager em = JpaUtil.getEntityManager();
    return em.createQuery("select p from Project p", Project.class).getResultList();
  }

  @Override
  public Project getProject(Long projectId) {
    EntityManager em = JpaUtil.getEntityManager();
    return em.find(Project.class, projectId);
  }

  @Override
  public void addProject(Project project) {
    EntityManager em = JpaUtil.getEntityManager();
    em.persist(project);
  }

  @Override
  public void updateProject(Project project) {
    EntityManager em = JpaUtil.getEntityManager();
    em.merge(project);
  }

  @Override
  public void deleteProject(Project project) {
    EntityManager em = JpaUtil.getEntityManager();
    em.remove(project);
  }
}
