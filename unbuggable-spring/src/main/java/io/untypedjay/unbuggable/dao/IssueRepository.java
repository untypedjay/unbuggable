package io.untypedjay.unbuggable.dao;

import io.untypedjay.unbuggable.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {

}
