package io.untypedjay.unbuggable.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class LogbookEntry implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long              id;

  private String            activity;

  private LocalDateTime     startTime;

  private LocalDateTime     endTime;

  @Fetch(FetchMode.JOIN)
  @ManyToOne(cascade = { CascadeType.PERSIST,
      CascadeType.MERGE }, fetch = FetchType.EAGER, optional = false)
  private Employee          employee;

  public LogbookEntry() {
  }

  public LogbookEntry(String activity, LocalDateTime start, LocalDateTime end) {
    this.activity = activity;
    this.startTime = start;
    this.endTime = end;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getActivity() {
    return activity;
  }

  public void setActivity(String activity) {
    this.activity = activity;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public void detachEmployee() {
    if (this.employee != null) this.employee.getLogbookEntries().remove(this);

    this.employee = null;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime start) {
    this.startTime = start;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime end) {
    this.endTime = end;
  }

  @Override
  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return activity + ": " + startTime.format(formatter) + " - " + endTime.format(formatter) +
        " (" + getEmployee().getLastName() + ")";
  }
}
