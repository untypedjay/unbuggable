package io.untypedjay.services;

import io.untypedjay.dao.ProjectDao;
import io.untypedjay.domain.Project;
import io.untypedjay.util.PersistenceManager;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {
  private static PersistenceManager persistenceManager = PersistenceManager.getInstance();
  @Override
  public List<Project> getAll() {
    List<Project> projects;
    try {
      persistenceManager.beginTransaction();
      ProjectDao projectDao = persistenceManager.getProjectDao();
      projects = projectDao.getAllProjects();
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }

    return projects;
  }

  @Override
  public void add(String name) {
    try {
      persistenceManager.beginTransaction();
      ProjectDao projectDao = persistenceManager.getProjectDao();
      projectDao.addProject(new Project(name));
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }
  }

  @Override
  public void update(Long Id, String name) {
    try {
      persistenceManager.beginTransaction();
      ProjectDao projectDao = persistenceManager.getProjectDao();
      Project project = projectDao.getProject(Id);
      project.setName(name);
      projectDao.updateProject(project);
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }
  }

  @Override
  public void remove(Long projectId) {
    try {
      persistenceManager.beginTransaction();
      ProjectDao projectDao = persistenceManager.getProjectDao();
      Project project = projectDao.getProject(projectId);
      projectDao.deleteProject(project);
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }
  }
}
