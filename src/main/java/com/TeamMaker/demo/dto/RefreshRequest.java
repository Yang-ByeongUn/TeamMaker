package com.TeamMaker.demo.dto;

import lombok.Data;

@Data
public class RefreshRequest {

  String token;

  public String refreshToken() {
    return token;
  }
}
