package io.untypedjay.domain;

import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LogbookEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;
  private String activity;
  private LocalDateTime startTime;
  private LocalDateTime endTime;

  @org.hibernate.annotations.Fetch(FetchMode.JOIN)
  @ManyToOne(fetch = FetchType.EAGER)
  private Employee employee;

  public LogbookEntry() {
  }

  public LogbookEntry(String activity, LocalDateTime startTime, LocalDateTime endTime) {
    this.activity = activity;
    this.startTime = startTime;
    this.endTime = endTime;
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

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  @Override
  public String toString() {
    return "LogbookEntry{" +
      "id=" + id +
      ", activity='" + activity + '\'' +
      ", startTime=" + startTime +
      ", endTime=" + endTime +
      '}';
  }
}
