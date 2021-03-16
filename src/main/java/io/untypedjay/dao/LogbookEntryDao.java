package io.untypedjay.dao;

import io.untypedjay.domain.LogbookEntry;

import java.util.List;

public interface LogbookEntryDao {
  List<LogbookEntry> getAllLogbookEntries();
  LogbookEntry getLogbookEntry(Long logbookEntryId);
  void addLogbookEntry(LogbookEntry logbookEntry);
  void updateLogbookEntry(LogbookEntry logbookEntry);
  void deleteLogbookEntry(LogbookEntry logbookEntry);
}
