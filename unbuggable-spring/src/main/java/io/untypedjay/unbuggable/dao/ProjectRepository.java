package io.untypedjay.unbuggable.dao;

import io.untypedjay.unbuggable.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
