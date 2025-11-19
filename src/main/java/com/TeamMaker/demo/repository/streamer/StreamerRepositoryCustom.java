package com.TeamMaker.demo.repository.streamer;

import com.TeamMaker.demo.entity.Position;
import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.repository.Direction;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface StreamerRepositoryCustom {

  List<Streamer> findStreamerByCondition(String streamerName, Direction streamerNameDirection, Double fromScore, Double endScore,
      Direction scoreDirection, Position position, UUID cursorId, Double cursorScore, String cursorName, int size);
}
