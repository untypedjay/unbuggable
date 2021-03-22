package io.untypedjay.unbuggable.dao;

import io.untypedjay.unbuggable.domain.LogbookEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogbookEntryRepository extends JpaRepository<LogbookEntry, Long> {

}
