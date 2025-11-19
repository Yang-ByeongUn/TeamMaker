package com.TeamMaker.demo.dto;

import com.TeamMaker.demo.entity.Tier;
import java.util.UUID;
import lombok.Data;

@Data
public class NicknameUpdateDto {

  private UUID nicknameId;
  private UUID streamerId;
  private String nickname;
  private Tier tier;

  public NicknameUpdateDto(UUID nicknameId, NicknameDto nicknameDto) {
    this.nicknameId = nicknameId;
    this.streamerId = nicknameDto.getStreamerId();
    this.nickname = nicknameDto.getNickname();
    this.tier = nicknameDto.getTier();
  }
}
