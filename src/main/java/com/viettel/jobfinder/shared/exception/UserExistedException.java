package com.viettel.jobfinder.shared.exception;

public class UserExistedException extends RuntimeException {
  public UserExistedException(String username) {
    super("User " + username + " already exists");
  }

}
