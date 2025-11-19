package com.TeamMaker.demo.repository.teamBoard;

import com.TeamMaker.demo.entity.TeamBoard;
import java.util.List;
import java.util.UUID;

public interface TeamBoardRepositoryCustom {

  List<TeamBoard> findTeamBoardsByStreamerId(UUID streamerId);
}
