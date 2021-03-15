package io.untypedjay.dao;

import io.untypedjay.domain.Issue;

import java.util.List;

public interface IssueDao {
  public List<Issue> getAllIssues();
  public Issue getEmployee(Long issueId);
  public void addIssue(Issue issue);
  public void updateIssue(Issue issue);
  public void deleteIssue(Issue issue);
}
