package com.TeamMaker.demo.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class TeamBoardUpdateDto {
  private  UUID teamBoardId;
  private String title;
  private  UUID topId;
  private  UUID jgId;
  private  UUID midId;
  private  UUID adId;
  private  UUID supId;
}
