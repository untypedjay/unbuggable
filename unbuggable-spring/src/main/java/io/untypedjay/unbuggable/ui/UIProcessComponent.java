package io.untypedjay.unbuggable.ui;

import io.untypedjay.unbuggable.domain.Employee;
import io.untypedjay.unbuggable.logic.WorkLogFacade;

// UIProcessComponent surrounds all of its methods with a SessionInterceptor
// that keeps the session open. This is important because lazy loading only works
// when an object's session is open. Otherwise you would get a LazyLoadingException
// when child elements are loaded lazily.
public class UIProcessComponent implements UIProcessFacade {

  private WorkLogFacade workLog;

  public void setWorkLog(WorkLogFacade workLog) {
    this.workLog = workLog;
  }

  @Override
  public void saveEmployees(Employee... employees) {
    for (Employee e : employees)
      workLog.syncEmployee(e);
  }

  @Override
  public void findAll() {
    for (Employee e : workLog.findAllEmployees()) {
      System.out.println(e);
      e.getLogbookEntries().forEach(entry -> System.out.println("   " + entry));
    }
  }
}
