package io.untypedjay.services;

import io.untypedjay.domain.Project;

import java.util.List;

public interface ProjectService {
  List<Project> getAll();
  void add(String name);
  void update(Long Id, String name);
  void remove(Long projectId);
}
