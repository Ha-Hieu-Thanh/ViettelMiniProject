package com.viettel.jobfinder.shared;

public enum ERoleName {
  EMPLOYEE("EMPLOYEE"),
  EMPLOYER("EMPLOYER");

  private final String value;

  ERoleName(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}