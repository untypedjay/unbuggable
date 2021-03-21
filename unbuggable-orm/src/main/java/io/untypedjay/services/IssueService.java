package io.untypedjay.services;

import io.untypedjay.domain.Issue;

import java.util.List;

public interface IssueService {
  List<Issue> getAll();
  void add(String priority, String estimatedCompletionTime);
  void remove(Long issueId);
}
