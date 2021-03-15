package io.untypedjay.domain;

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



  enum State {
    NEW,
    OPEN,
    RESOLVED,
    CLOSED,
    REJECTED
  }

  enum Priority {
    LOW,
    NORMAL,
    HIGH
  }
}
