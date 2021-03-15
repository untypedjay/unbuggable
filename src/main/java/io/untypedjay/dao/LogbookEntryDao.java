package io.untypedjay.dao;

import io.untypedjay.domain.LogbookEntry;

import java.util.List;

public interface LogbookEntryDao {
  public List<LogbookEntry> getAllLogbookEntries();
  public LogbookEntry getLogbookEntry(Long logbookEntryId);
  public void addLogbookEntry(LogbookEntry logbookEntry);
  public void updateLogbookEntry(LogbookEntry logbookEntry);
  public void deleteLogbookEntry(LogbookEntry logbookEntry);
}
