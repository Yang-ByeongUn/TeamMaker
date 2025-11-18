package com.TeamMaker.demo.repository.streamer;

import com.TeamMaker.demo.entity.Position;
import com.TeamMaker.demo.entity.QStreamer;
import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.entity.Tier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StreamRepositoryImpl implements StreamerRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  private final QStreamer qStreamer = QStreamer.streamer;
  @Override
  public List<Streamer> findStreamerByCondition(String streamerName, Double score, Position position) {
    return queryFactory.selectFrom(qStreamer).where(eqStreamerName(streamerName), eqScore(score), eqPosition(position)).fetch();
  }
  private BooleanExpression eqStreamerName(String streamerName) {
    return streamerName != null ? qStreamer.streamerName.eq(streamerName) : null;
  }

  private BooleanExpression eqScore(Double score) {
    return score != null ? qStreamer.score.eq(score) : null;
  }

  private BooleanExpression eqPosition(Position position) {
    return position != null ? qStreamer.position.eq(position) : null;
  }
}
