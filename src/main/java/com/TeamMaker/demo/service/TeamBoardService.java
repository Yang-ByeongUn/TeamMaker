package com.TeamMaker.demo.service;

import com.TeamMaker.demo.dto.TeamBoardSaveDto;
import com.TeamMaker.demo.dto.TeamBoardUpdateDto;
import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.entity.TeamBoard;
import com.TeamMaker.demo.repository.streamer.StreamerRepository;
import com.TeamMaker.demo.repository.teamBoard.TeamBoardRepository;
import com.TeamMaker.demo.utils.exception.ErrorCode;
import com.TeamMaker.demo.utils.exception.streamer.StreamerNotFoundException;
import com.TeamMaker.demo.utils.exception.teamBoard.TeamBoardNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamBoardService {

  private final TeamBoardRepository teamBoardRepository;
  private final StreamerRepository streamerRepository;

  public TeamBoard save(TeamBoardSaveDto teamBoardSaveDto) {
    TeamBoard teamBoard = new TeamBoard(teamBoardSaveDto);
    return teamBoardRepository.save(teamBoard);
  }

  public TeamBoard findById(UUID id) {
    return teamBoardRepository.findById(id).orElseThrow(() -> new TeamBoardNotFoundException(ErrorCode.TEAM_BOARD_NOT_FOUND));
  }

  @Transactional
  public UUID updateTeamBoard(TeamBoardUpdateDto teamBoardUpdateDto) {
    TeamBoard teamBoard = teamBoardRepository.findById(teamBoardUpdateDto.getTeamBoardId())
        .orElseThrow(() -> new TeamBoardNotFoundException(ErrorCode.TEAM_BOARD_NOT_FOUND));
    teamBoard.update(teamBoardUpdateDto);
    teamBoard.setSumScore(calculatorScore(teamBoardUpdateDto));
    return teamBoard.getId();
  }

  private double calculatorScore(TeamBoardUpdateDto teamBoardUpdateDto) {
    double init = 187.0;
    List<UUID> list = new ArrayList<>();
    list.add(teamBoardUpdateDto.getTopId());
    list.add(teamBoardUpdateDto.getJgId());
    list.add(teamBoardUpdateDto.getMidId());
    list.add(teamBoardUpdateDto.getAdId());
    list.add(teamBoardUpdateDto.getSupId());
    for (UUID streamerId : list) {
      Streamer streamer = streamerRepository.findById(streamerId).orElseThrow(() -> new StreamerNotFoundException(ErrorCode.STREAMER_NOT_FOUND));
      init -= streamer.getScore();
    }
    return init;
  }

  public void deleteTeamBoard(UUID id) {
    teamBoardRepository.deleteById(id);
  }
}
