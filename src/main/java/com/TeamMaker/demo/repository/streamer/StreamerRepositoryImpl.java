package com.TeamMaker.demo.repository.streamer;

import com.TeamMaker.demo.entity.Position;
import com.TeamMaker.demo.entity.QStreamer;
import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.repository.Direction;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StreamerRepositoryImpl  implements StreamerRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QStreamer qStreamer = QStreamer.streamer;

  @Override
  public List<Streamer> findStreamerByCondition(String streamerName, Direction streamerNameDirection, Double fromScore, Double endScore,
      Direction scoreDirection, Position position, UUID cursorId, Double cursorScore, String cursorName, int size) {
    return queryFactory.selectFrom(qStreamer).where(containsStreamerName(streamerName), betweenScore(fromScore, endScore), eqPosition(position),
            cursorCondition(cursorName, streamerNameDirection, cursorScore, scoreDirection, cursorId))
        .orderBy(orderByName(streamerNameDirection), orderByScore(scoreDirection), qStreamer.id.asc()).limit(size).fetch();
  }

  // ---------- 필터 조건 ----------

  private BooleanExpression containsStreamerName(String streamerName) {
    return streamerName != null ? qStreamer.streamerName.contains(streamerName) : null;
  }

  private BooleanExpression betweenScore(Double fromScore, Double endScore) {
    if (fromScore != null && endScore != null) {
      return qStreamer.score.between(fromScore, endScore);
    } else if (fromScore == null && endScore != null) {
      return qStreamer.score.loe(endScore);
    } else if (fromScore != null) {
      return qStreamer.score.goe(fromScore);
    }
    return null;
  }

  private BooleanExpression eqPosition(Position position) {
    return position != null ? qStreamer.position.eq(position) : null;
  }

  // ---------- 정렬 도우미 ----------

  private OrderSpecifier<String> orderByName(Direction direction) {
    if (direction == null || direction == Direction.ASC) {
      return qStreamer.streamerName.asc();
    } else {
      return qStreamer.streamerName.desc();
    }
  }

  private OrderSpecifier<Double> orderByScore(Direction direction) {
    if (direction == null || direction == Direction.ASC) {
      return qStreamer.score.asc();
    } else {
      return qStreamer.score.desc();
    }
  }

  // ---------- 커서 조건 ----------
  private BooleanExpression cursorCondition(String cursorName, Direction streamerNameDirection, Double cursorScore, Direction scoreDirection,
      UUID cursorId) {
    if (cursorName == null || cursorScore == null || cursorId == null) {
      return null;
    }

    BooleanExpression nameAfter;
    BooleanExpression nameEqual = qStreamer.streamerName.eq(cursorName);
    if (streamerNameDirection == null || streamerNameDirection == Direction.ASC) {
      nameAfter = qStreamer.streamerName.gt(cursorName);
    } else {
      nameAfter = qStreamer.streamerName.lt(cursorName);
    }

    // score 쪽 비교
    BooleanExpression scoreAfter;
    BooleanExpression scoreEqual = qStreamer.score.eq(cursorScore);
    if (scoreDirection == null || scoreDirection == Direction.ASC) {
      // 점수 오름차순: score > cursorScore 가 "뒤쪽"
      scoreAfter = qStreamer.score.gt(cursorScore);
    } else {
      // 점수 내림차순: score < cursorScore 가 "뒤쪽"
      scoreAfter = qStreamer.score.lt(cursorScore);
    }

    BooleanExpression idAfter = qStreamer.id.gt(cursorId);

    return nameAfter.or(nameEqual.and(scoreAfter)).or(nameEqual.and(scoreEqual).and(idAfter));
  }
}
