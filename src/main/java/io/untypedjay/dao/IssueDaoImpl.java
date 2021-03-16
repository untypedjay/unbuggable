package io.untypedjay.dao;

import io.untypedjay.domain.Issue;
import io.untypedjay.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class IssueDaoImpl implements IssueDao {
  @Override
  public List<Issue> getAllIssues() {
    EntityManager em = JpaUtil.getEntityManager();
    return em.createQuery("select i from Issue i", Issue.class).getResultList();
  }

  @Override
  public Issue getIssue(Long issueId) {
    EntityManager em = JpaUtil.getEntityManager();
    return em.find(Issue.class, issueId);
  }

  @Override
  public void addIssue(Issue issue) {
    EntityManager em = JpaUtil.getEntityManager();
    em.persist(issue);
  }

  @Override
  public void updateIssue(Issue issue) {
    EntityManager em = JpaUtil.getEntityManager();
    em.merge(issue);
  }

  @Override
  public void deleteIssue(Issue issue) {
    EntityManager em = JpaUtil.getEntityManager();
    em.remove(issue);
  }
}
