package com.TeamMaker.demo.dto;

import lombok.Data;

@Data
public class LoginResponse {
  String access_token;
  String refresh_token;

  public LoginResponse(String access_token, String refresh_token) {
    this.access_token = access_token;
    this.refresh_token = refresh_token;
  }
}
