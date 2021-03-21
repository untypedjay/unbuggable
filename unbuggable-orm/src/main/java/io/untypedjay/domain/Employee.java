package io.untypedjay.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("E")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Long id;

  @Column(length = 20)
  private String firstName;

  @Column(nullable = false, length = 40)
  private String lastName;

  @Column(nullable = false)
  private LocalDate dateOfBirth;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
  private Set<LogbookEntry> logbookEntries = new HashSet<>();

  @Embedded
  @AttributeOverride(name = "zipCode", column = @Column(name="ADDR_ZIPCODE", length = 10))
  @AttributeOverride(name = "city", column = @Column(name="ADDR_CITY"))
  @AttributeOverride(name = "street", column = @Column(name="ADDR_STREET"))
  private Address address;

  public Employee() { }

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

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Set<LogbookEntry> getLogbookEntries() {
    return logbookEntries;
  }

  public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
    this.logbookEntries = logbookEntries;
  }

  public void addLogbookEntry(LogbookEntry entry) {
    if (entry == null) {
      throw new IllegalArgumentException("Null LogbookEntry");
    }

    if (entry.getEmployee() != null) {
      entry.getEmployee().getLogbookEntries().remove(entry);
    }

    this.logbookEntries.add(entry);
    entry.setEmployee(this);
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "Employee{" +
      "id=" + id +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", dateOfBirth=" + dateOfBirth +
      ", address=" + address +
      '}';
  }
}
