package com.TeamMaker.demo.dto;

import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.entity.TeamBoard;
import jakarta.annotation.Nullable;
import java.util.UUID;
import lombok.Data;

@Data
public class TeamBoardDto {

  private UUID id;
  private UUID userId;
  private String title;
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
  private double sumScore;

  public TeamBoardDto(TeamBoard teamBoard) {
    this.id = teamBoard.getId();
    this.userId = teamBoard.getStreamer().getId();
    this.title = teamBoard.getTitle();
    this.topId = teamBoard.getTopId();
    this.jgId = teamBoard.getJgId();
    this.midId = teamBoard.getMidId();
    this.adId = teamBoard.getAdId();
    this.supId = teamBoard.getSupId();
    this.sumScore = teamBoard.getSumScore();
  }
}
