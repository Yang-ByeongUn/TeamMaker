package com.TeamMaker.demo.dto;

import com.TeamMaker.demo.entity.Position;
import com.TeamMaker.demo.entity.Streamer;
import lombok.Data;

@Data
public class StreamerDto {
  public String streamerName;
  public Position position;
  public Double score;

  public StreamerDto(Streamer streamer) {
    this.streamerName = streamer.getStreamerName();
    this.position = streamer.getPosition();
    this.score = streamer.getScore();
  }
}
