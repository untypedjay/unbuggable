package io.untypedjay.unbuggable.ui;

import io.untypedjay.unbuggable.domain.Employee;

// Interface that should be used by rich clients. Implementations
// of this interface can hold the state of the user interface.
// All event handlers should invoke methods of UIProcessFacade 
// if they access business logic.
public interface UIProcessFacade {
  public void saveEmployees(Employee... empls);
  public void findAll();
}
