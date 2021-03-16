package io.untypedjay.dao;

import io.untypedjay.domain.LogbookEntry;
import io.untypedjay.util.JpaUtil;
import org.hibernate.annotations.common.util.impl.Log;

import javax.persistence.EntityManager;
import java.util.List;

public class LogbookEntryDaoImpl implements LogbookEntryDao
{
  @Override
  public List<LogbookEntry> getAllLogbookEntries() {
    EntityManager em = JpaUtil.getEntityManager();
    return em.createQuery("select le from LogbookEntry le", LogbookEntry.class).getResultList();
  }

  @Override
  public LogbookEntry getLogbookEntry(Long logbookEntryId) {
    EntityManager em = JpaUtil.getEntityManager();
    return em.find(LogbookEntry.class, logbookEntryId);
  }

  @Override
  public void addLogbookEntry(LogbookEntry logbookEntry) {
    EntityManager em = JpaUtil.getEntityManager();
    em.persist(logbookEntry);
  }

  @Override
  public void updateLogbookEntry(LogbookEntry logbookEntry) {
    EntityManager em = JpaUtil.getEntityManager();
    em.merge(logbookEntry);
  }

  @Override
  public void deleteLogbookEntry(LogbookEntry logbookEntry) {
    EntityManager em = JpaUtil.getEntityManager();
    em.remove(logbookEntry);
  }
}
