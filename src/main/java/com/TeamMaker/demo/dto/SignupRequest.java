package com.TeamMaker.demo.dto;

import com.TeamMaker.demo.entity.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
  private String username;
  private String password;
  private double score;
  private String streamerName;
  private Position position;
}

