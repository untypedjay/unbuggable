package io.untypedjay.util;

import io.untypedjay.dao.*;

import javax.persistence.EntityManager;

public class PersistenceManager {
  private static PersistenceManager persistenceManager = null;

  private PersistenceManager() { }

  public static PersistenceManager getInstance() {
    if (persistenceManager == null) {
      persistenceManager = new PersistenceManager();
    }
    return persistenceManager;
  }

  public void beginTransaction() {
    EntityManager em = JpaUtil.getTransactedEntityManager();
    // TODO
  }

  public EmployeeDao getEmployeeDao() {
    return new EmployeeDaoImpl();
  }

  public IssueDao getIssueDao() {
    return new IssueDaoImpl();
  }

  public LogbookEntryDao getLogbookEntryDao() {
    return new LogbookEntryDaoImpl();
  }

  public ProjectDao getProjectDao() {
    return new ProjectDaoImpl();
  }

  public void commit() {
    JpaUtil.commit();
  }

  public void rollback() {
    JpaUtil.rollback();
  }
}
