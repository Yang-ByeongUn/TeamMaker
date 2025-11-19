package com.TeamMaker.demo.repository.teamBoard;

import com.TeamMaker.demo.entity.QTeamBoard;
import com.TeamMaker.demo.entity.TeamBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TeamBoardRepositoryImpl implements TeamBoardRepositoryCustom{
  private final JPAQueryFactory queryFactory;
  private final QTeamBoard teamBoard = QTeamBoard.teamBoard;
  @Override
  public List<TeamBoard> findTeamBoardsByStreamerId(UUID streamerId) {
    return queryFactory.selectFrom(teamBoard).where(teamBoard.streamer.id.eq(streamerId)).orderBy(teamBoard.id.asc()).fetch();
  }
}
