package com.TeamMaker.demo.repository.nickname;

import com.TeamMaker.demo.entity.Nickname;
import com.TeamMaker.demo.entity.Tier;
import java.util.List;
import java.util.UUID;

public interface NicknameRepositoryCustom {
  List<Nickname> findNicknamesByConditions(UUID streamerId, String nicknameStr, Tier tier);
}
