package io.untypedjay.dao;

import io.untypedjay.domain.Issue;

import java.util.List;

public interface IssueDao {
  List<Issue> getAllIssues();
  Issue getEmployee(Long issueId);
  void addIssue(Issue issue);
  void updateIssue(Issue issue);
  void deleteIssue(Issue issue);
}
