package com.TeamMaker.demo.service;

import static com.TeamMaker.demo.utils.exception.ErrorCode.STREAMER_NOT_FOUND;

import com.TeamMaker.demo.dto.StreamerSaveDto;
import com.TeamMaker.demo.entity.Position;
import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.repository.streamer.StreamerRepository;
import com.TeamMaker.demo.utils.exception.streamer.StreamerNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StreamerService {

  public final StreamerRepository streamerRepository;
  @Transactional
  public Streamer save(StreamerSaveDto streamerSaveDto) {
    Streamer streamer = new Streamer(streamerSaveDto);
    return streamerRepository.save(streamer);
  }

  public Streamer findById(UUID id) {
    return streamerRepository.findById(id).orElseThrow( () -> new StreamerNotFoundException(STREAMER_NOT_FOUND));
  }
  public List<Streamer> findByUsernameAndScoreAndPosition(String username, double score, Position position) {
    return streamerRepository.findStreamerByCondition(username, score, position);
  }
  public void deleteStreamer(UUID id) {
    streamerRepository.deleteById(id);
  }
}
