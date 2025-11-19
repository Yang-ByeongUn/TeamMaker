package com.TeamMaker.demo.entity;

import com.TeamMaker.demo.dto.TeamBoardSaveDto;
import com.TeamMaker.demo.dto.TeamBoardUpdateDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teamboards")
@RequiredArgsConstructor
@Getter
public class TeamBoard {
  @Id @GeneratedValue
  private UUID id;
  private String title;
  @ManyToOne(fetch = FetchType.LAZY)
  private Streamer streamer;
  @Nullable
  private UUID topId;
  @Nullable
  private UUID jgId;
  @Nullable
  private UUID midId;
  @Nullable
  private UUID adId;
  @Nullable
  private UUID supId;
  @Setter
  private double sumScore;

  public TeamBoard(Streamer streamer, String title) {
    this.streamer = streamer;
    this.title = title;
    this.sumScore = 187;
  }

  public void update(TeamBoardUpdateDto teamBoardUpdateDto) {
    this.title = teamBoardUpdateDto.getTitle();
    topId = teamBoardUpdateDto.getTopId();
    jgId = teamBoardUpdateDto.getJgId();
    midId = teamBoardUpdateDto.getMidId();
    adId = teamBoardUpdateDto.getAdId();
    supId = teamBoardUpdateDto.getSupId();
  }
}
