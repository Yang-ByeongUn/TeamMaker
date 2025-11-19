package com.TeamMaker.demo.dto;

import com.TeamMaker.demo.entity.Position;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StreamerSaveDto {
  public double score;
  public String streamerName;
  public Position position;
  public LocalDateTime createdAt;

  public StreamerSaveDto(double score, String streamerName, Position position, LocalDateTime createdAt) {
    this.score = score;
    this.streamerName = streamerName;
    this.position = position;
    this.createdAt = createdAt;
  }
}
