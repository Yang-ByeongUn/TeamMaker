package com.TeamMaker.demo.repository.streamer;

import com.TeamMaker.demo.entity.Position;
import com.TeamMaker.demo.entity.Streamer;
import java.util.List;

public interface StreamerRepositoryCustom {
  List<Streamer> findStreamerByCondition(String streamerName, Double score, Position position);
}
