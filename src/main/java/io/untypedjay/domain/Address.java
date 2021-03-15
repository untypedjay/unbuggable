package io.untypedjay.domain;

import javax.persistence.*;
import java.io.Serializable;

//@Entity // approach 2
@Embeddable
public class Address implements Serializable {
  private static final long serialVersionUID = 1L;

  // approach 2
//  @Id
//  @GeneratedValue(strategy = GenerationType.TABLE)
//  private Long     id;
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

  // approach 2
//  public Long getId() {
//    return id;
//  }
//
//  public void setId(Long id) {
//    this.id = id;
//  }

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
