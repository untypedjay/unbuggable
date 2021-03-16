package io.untypedjay.services;

import io.untypedjay.dao.IssueDao;
import io.untypedjay.domain.Issue;
import io.untypedjay.util.PersistenceManager;
import io.untypedjay.util.TimeUtil;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

public class IssueServiceImpl implements IssueService {
  private static PersistenceManager persistenceManager = PersistenceManager.getInstance();

  @Override
  public List<Issue> getAll() {
    List<Issue> issues;
    try {
      persistenceManager.beginTransaction();
      IssueDao issueDao = persistenceManager.getIssueDao();
      issues = issueDao.getAllIssues();
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }

    return issues;
  }

  @Override
  public void add(String unparsedPriority, String estimatedCompletionTime) {
    try {
      Issue.Priority priority = Issue.Priority.valueOf(unparsedPriority.toUpperCase());
      Duration estimation = TimeUtil.parseDuration(estimatedCompletionTime);
      persistenceManager.beginTransaction();
      IssueDao issueDao = persistenceManager.getIssueDao();
      issueDao.addIssue(new Issue(priority, estimation));
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }
  }

  @Override
  public void remove(Long issueId) {
    try {
      persistenceManager.beginTransaction();
      IssueDao issueDao = persistenceManager.getIssueDao();
      Issue issue = issueDao.getIssue(issueId);
      issueDao.deleteIssue(issue);
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }
  }
}
