package io.untypedjay.domain;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class Address implements Serializable {
  private static final long serialVersionUID = 1L;

  private String   zipCode;
  private String   city;
  private String   street;
  
  public Address() {  
  }

  public Address(String zipCode, String city, String street) {
    this.zipCode = zipCode;
    this.city = city;
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String toString() {
    return String.format("%s %s, %s", zipCode, city, street);
  }
}
