package io.untypedjay.services;

import io.untypedjay.domain.Project;

import java.util.List;

public interface ProjectService {
  public List<Project> getAll();
  public void add(String name);
  public void update(Long Id, String name);
  public void remove(Long projectId);
}
