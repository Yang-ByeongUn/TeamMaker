package com.TeamMaker.demo.service;

import static com.TeamMaker.demo.common.exception.ErrorCode.STREAMER_NOT_FOUND;

import com.TeamMaker.demo.dto.StreamerDto;
import com.TeamMaker.demo.dto.StreamerSaveDto;
import com.TeamMaker.demo.entity.Position;
import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.repository.Direction;
import com.TeamMaker.demo.repository.streamer.CursorPageResponse;
import com.TeamMaker.demo.repository.streamer.StreamerRepository;
import com.TeamMaker.demo.common.exception.streamer.StreamerNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StreamerService {

  public final StreamerRepository streamerRepository;

  @Transactional
  public Streamer save(StreamerSaveDto streamerSaveDto) {
    Streamer streamer = new Streamer(streamerSaveDto);
    return streamerRepository.save(streamer);
  }

  public Streamer findById(UUID id) {
    return streamerRepository.findById(id).orElseThrow(() -> new StreamerNotFoundException(STREAMER_NOT_FOUND));
  }

  public CursorPageResponse<StreamerDto> findStreamerWithCursor(String streamerName, Direction streamerNameDirection, Double fromScore,
      Double endScore, Direction scoreDirection, Position position, UUID cursorId, Double cursorScore, String cursorName, int size) {
    List<Streamer> list = streamerRepository.findStreamerByCondition(streamerName, streamerNameDirection, fromScore, endScore, scoreDirection,
        position, cursorId, cursorScore, cursorName, size + 1);
    boolean hasNext = list.size() == size + 1;
    if (hasNext) list = list.subList(0, size);
    String nextName = null;
    Double nextScore = null;
    UUID nextId = null;

    if (!list.isEmpty()) {
      Streamer last = list.get(list.size() - 1);
      nextName = last.getStreamerName();
      nextScore = last.getScore();
      nextId = last.getId();
    }
    return new CursorPageResponse<>(list.stream().map(StreamerDto::new).collect(Collectors.toList()), nextName, nextScore, nextId, hasNext);
  }

  @Transactional
  public void changeStreamerDetails(UUID streamerId, StreamerDto streamerDto) {
    Streamer streamer = findById(streamerId);
    streamer.setDetails(streamerDto.score, streamerDto.streamerName, streamerDto.position);
  }

  @Transactional
  public void deleteStreamer(UUID id) {
    streamerRepository.deleteById(id);
  }
}
