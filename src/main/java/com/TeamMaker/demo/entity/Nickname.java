package com.TeamMaker.demo.entity;

import com.TeamMaker.demo.dto.NicknameSaveDto;
import com.TeamMaker.demo.dto.NicknameUpdateDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "nicknames")
@RequiredArgsConstructor
@Getter
public class Nickname {

  @Id @GeneratedValue(strategy = GenerationType.UUID)
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

  public void update(Streamer streamer, NicknameUpdateDto nicknameUpdateDto) {
    this.streamer = streamer;
    this.nickname = nicknameUpdateDto.getNickname();
    this.tier = nicknameUpdateDto.getTier();
  }
}
