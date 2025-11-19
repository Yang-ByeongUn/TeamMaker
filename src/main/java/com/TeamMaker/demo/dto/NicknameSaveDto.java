package com.TeamMaker.demo.dto;

import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.entity.Tier;
import lombok.Data;

@Data
public class NicknameSaveDto {

  Streamer streamer;
  String nickname;
  Tier tier;

  public NicknameSaveDto(Streamer streamer, String nickname, Tier tier) {
    this.streamer = streamer;
    this.nickname = nickname;
    this.tier = tier;
  }
}
