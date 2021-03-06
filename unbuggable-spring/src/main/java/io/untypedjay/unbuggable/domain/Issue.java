package io.untypedjay.unbuggable.domain;

import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.Duration;

@Entity
public class Issue {
  @Id
  @GeneratedValue
  private Long id;

  private State state;

  private String name;

  @org.hibernate.annotations.Fetch(FetchMode.JOIN)
  @ManyToOne(fetch = FetchType.EAGER)
  private Project project;

  private Priority priority;

  private Duration estimatedTime = Duration.ofSeconds(0);

  private Duration expendedTime = Duration.ofSeconds(0);

  @org.hibernate.annotations.Fetch(FetchMode.JOIN)
  @ManyToOne(fetch = FetchType.EAGER)
  private Employee assignee;

  public Issue() { }

  public Issue(String name, Project project, Priority priority) {
    this.state = State.NEW;
    this.name = name;
    this.project = project;
    project.addIssue(this);
    this.priority = priority;
    this.assignee = null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public Duration getEstimatedTime() {
    return estimatedTime;
  }

  public void setEstimatedTime(Duration estimatedTime) {
    this.estimatedTime = estimatedTime;
  }

  public Duration getExpendedTime() {
    return expendedTime;
  }

  public void setExpendedTime(Duration expendedTime) {
    this.expendedTime = expendedTime;
  }

  public Employee getAssignee() {
    return assignee;
  }

  public void setAssignee(Employee assignee) {
    this.assignee = assignee;
  }

  public void addTime(Duration time) {
    this.expendedTime = this.expendedTime.plus(time);
  }

  public enum State {
    NEW,
    OPEN,
    RESOLVED,
    CLOSED,
    REJECTED
  }

  public enum Priority {
    LOW,
    NORMAL,
    HIGH
  }
}
