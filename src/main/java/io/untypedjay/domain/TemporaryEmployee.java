package io.untypedjay.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@DiscriminatorValue("TE")
public class TemporaryEmployee extends Employee {
  private static final long serialVersionUID = 1L;

  private String            renter;
  private double            hourlyRate;
  private LocalDate         startDate;
  private LocalDate         endDate;

  public TemporaryEmployee() {
  }

  public TemporaryEmployee(String firstName, String lastName, LocalDate dateOfBirth) {
    super(firstName, lastName, dateOfBirth);
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public double getHourlyRate() {
    return hourlyRate;
  }

  public void setHourlyRate(double hourlyRate) {
    this.hourlyRate = hourlyRate;
  }

  public String getRenter() {
    return renter;
  }

  public void setRenter(String renter) {
    this.renter = renter;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public String toString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    StringBuffer sb = new StringBuffer(super.toString());
    sb.append(", hourlyRate=" + hourlyRate);
    sb.append(", renter=" + renter);
    sb.append(", startDate=" + startDate.format(formatter));
    sb.append(", endDate=" + endDate.format(formatter));

    return sb.toString();
  }
}
