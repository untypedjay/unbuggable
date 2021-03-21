package io.untypedjay.unbuggable.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Employee implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  private String firstName;
  private String lastName;
  
  private LocalDate dateOfBirth;

  @OneToMany(mappedBy="employee", cascade=CascadeType.ALL, fetch=FetchType.LAZY, 
             orphanRemoval=true) 
  private Set<LogbookEntry> logbookEntries = new HashSet<>();

  public Employee() {  
  }
  
  public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
  }

  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }
  
  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth; 
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Set<LogbookEntry> getLogbookEntries() {
    return logbookEntries;
  }
  
  public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
    this.logbookEntries = logbookEntries;
  }
  
  public void addLogbookEntry(LogbookEntry entry) {
    if (entry.getEmployee() != null)
       entry.getEmployee().logbookEntries.remove(entry);
    this.logbookEntries.add(entry);
    entry.setEmployee(this);
  }

  public void removeLogbookEntry(LogbookEntry entry) {
    this.logbookEntries.remove(entry);
  }

	public String toString() {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return String.format("%d: %s, %s (%s)", 
                         id, lastName, firstName, dateOfBirth.format(fmt));
  }
}