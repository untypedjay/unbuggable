package io.untypedjay.services;

import io.untypedjay.domain.LogbookEntry;

import java.util.List;

public interface LogbookEntryService {
  List<LogbookEntry> getAll();
  void add(String activity, String unparsedStartTime, String unparsedEndTime);
  void remove(Long logbookEntryId);
}
