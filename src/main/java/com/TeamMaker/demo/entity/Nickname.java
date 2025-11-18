package com.TeamMaker.demo.entity;

import com.TeamMaker.demo.dto.NicknameSaveDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "nicknames")
@RequiredArgsConstructor
public class Nickname {

  @Id
  private UUID id;
  @ManyToOne(fetch = FetchType.LAZY)
  private Streamer streamer;
  private String nickname;
  @Enumerated(EnumType.STRING)
  private Tier tier;

  public Nickname(NicknameSaveDto nicknameSaveDto) {
    this.streamer = nicknameSaveDto.getStreamer();
    this.nickname = nicknameSaveDto.getNickname();
    this.tier = nicknameSaveDto.getTier();
  }
}
