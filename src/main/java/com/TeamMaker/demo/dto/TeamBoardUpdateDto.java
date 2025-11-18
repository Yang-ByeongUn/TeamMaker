package com.TeamMaker.demo.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class TeamBoardUpdateDto {
  public UUID teamBoardId;
  public UUID topId;
  public UUID jgId;
  public UUID midId;
  public UUID adId;
  public UUID supId;
}
