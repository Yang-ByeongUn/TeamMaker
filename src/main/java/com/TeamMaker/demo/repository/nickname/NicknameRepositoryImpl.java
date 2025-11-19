package com.TeamMaker.demo.repository.nickname;

import com.TeamMaker.demo.entity.Nickname;
import com.TeamMaker.demo.entity.QNickname;
import com.TeamMaker.demo.entity.Tier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NicknameRepositoryImpl implements NicknameRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QNickname nickname = QNickname.nickname1;

  public List<Nickname> findNicknamesByConditions(UUID streamerId, String nicknameStr, Tier tier) {
    return queryFactory.selectFrom(nickname).where(eqStreamerId(streamerId), eqNickname(nicknameStr), eqTier(tier))
        .orderBy(nickname.tier.desc(), nickname.id.asc()).fetch();
  }

  private BooleanExpression eqStreamerId(UUID streamerId) {
    return streamerId != null ? nickname.streamer.id.eq(streamerId) : null;
  }

  private BooleanExpression eqNickname(String nicknameStr) {
    return (nicknameStr != null && !nicknameStr.isEmpty()) ? nickname.nickname.eq(nicknameStr) : null;
  }

  private BooleanExpression eqTier(Tier tier) {
    return tier != null ? nickname.tier.eq(tier) : null;
  }
}
