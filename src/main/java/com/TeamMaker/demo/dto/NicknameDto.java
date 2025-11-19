package com.TeamMaker.demo.dto;

import com.TeamMaker.demo.entity.Nickname;
import com.TeamMaker.demo.entity.Tier;
import java.util.UUID;
import lombok.Data;

@Data
public class NicknameDto {
  private UUID nicknameId;
  private UUID streamerId;
  private String nickname;
  private Tier tier;

  public NicknameDto(Nickname nickname) {
    this.nicknameId = nickname.getId();
    this.streamerId = nickname.getStreamer().getId();
    this.nickname = nickname.getNickname();
    this.tier = nickname.getTier();
  }
}
