package io.untypedjay.dao;

import io.untypedjay.domain.Project;

import java.util.List;

public interface ProjectDao {
  List<Project> getAllProjects();
  Project getProject(Long projectId);
  void addProject(Project project);
  void updateProject(Project project);
  void deleteProject(Project project);
}
