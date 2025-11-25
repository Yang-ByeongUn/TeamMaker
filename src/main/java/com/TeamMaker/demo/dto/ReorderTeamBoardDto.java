package com.TeamMaker.demo.dto;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class SwapTeamBoardDto {
  UUID teamBoard1;
  UUID teamBoard2;
}
