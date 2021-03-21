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
  private Priority priority;
  private Duration estimatedCompletionTime;
  private double progress;
  @org.hibernate.annotations.Fetch(FetchMode.JOIN)
  @ManyToOne(fetch = FetchType.EAGER)
  private Employee assignee;

  public Issue() { }

  public Issue(Priority priority, Duration estimatedCompletionTime) {
    this.state = State.NEW;
    this.priority = priority;
    this.estimatedCompletionTime = estimatedCompletionTime;
    this.progress = 0.0;
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

  public Priority getPriority() {
    return priority;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  public Duration getEstimatedCompletionTime() {
    return estimatedCompletionTime;
  }

  public void setEstimatedCompletionTime(Duration estimatedCompletionTime) {
    this.estimatedCompletionTime = estimatedCompletionTime;
  }

  public double getProgress() {
    return progress;
  }

  public void setProgress(double progress) {
    this.progress = progress;
  }

  public Employee getAssignee() {
    return assignee;
  }

  public void setAssignee(Employee assignee) {
    this.assignee = assignee;
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
