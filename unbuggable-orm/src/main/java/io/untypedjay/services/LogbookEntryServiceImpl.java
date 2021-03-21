package io.untypedjay.services;

import io.untypedjay.dao.LogbookEntryDao;
import io.untypedjay.domain.LogbookEntry;
import io.untypedjay.util.PersistenceManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LogbookEntryServiceImpl implements LogbookEntryService {
  private static PersistenceManager persistenceManager = PersistenceManager.getInstance();
  @Override
  public List<LogbookEntry> getAll() {
    List<LogbookEntry> logbookEntries;
    try {
      persistenceManager.beginTransaction();
      LogbookEntryDao logbookEntryDao = persistenceManager.getLogbookEntryDao();
      logbookEntries = logbookEntryDao.getAllLogbookEntries();
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }

    return logbookEntries;
  }

  @Override
  public void add(String activity, String unparsedStartTime, String unparsedEndTime) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
      LocalDateTime startTime = LocalDateTime.parse(unparsedStartTime, formatter);
      LocalDateTime endTime = LocalDateTime.parse(unparsedEndTime, formatter);
      persistenceManager.beginTransaction();
      LogbookEntryDao logbookEntryDao = persistenceManager.getLogbookEntryDao();
      logbookEntryDao.addLogbookEntry(new LogbookEntry(activity, startTime, endTime));
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }
  }

  @Override
  public void remove(Long logbookEntryId) {
    try {
      persistenceManager.beginTransaction();
      LogbookEntryDao logbookEntryDao = persistenceManager.getLogbookEntryDao();
      LogbookEntry logbookEntry = logbookEntryDao.getLogbookEntry(logbookEntryId);
      logbookEntryDao.deleteLogbookEntry(logbookEntry);
      persistenceManager.commit();
    } catch (Exception e) {
      persistenceManager.rollback();
      throw e;
    }
  }
}
