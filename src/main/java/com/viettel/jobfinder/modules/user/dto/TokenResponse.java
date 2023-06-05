package com.viettel.jobfinder.modules.user.dto;

import lombok.Data;

@Data
public class TokenResponse {
  private String access_token;
  private String refresh_token;

  public TokenResponse(String access_token, String refresh_token) {
    this.access_token = access_token;
    this.refresh_token = refresh_token;
  }
}
