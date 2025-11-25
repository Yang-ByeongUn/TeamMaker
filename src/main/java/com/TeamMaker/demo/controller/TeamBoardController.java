package com.TeamMaker.demo.controller;

import com.TeamMaker.demo.common.utils.security.CustomUserDetails;
import com.TeamMaker.demo.common.utils.security.User;
import com.TeamMaker.demo.common.utils.security.repository.UserRepository;
import com.TeamMaker.demo.dto.TeamBoardDto;
import com.TeamMaker.demo.dto.TeamBoardSaveDto;
import com.TeamMaker.demo.dto.TeamBoardUpdateDto;
import com.TeamMaker.demo.entity.TeamBoard;
import com.TeamMaker.demo.service.TeamBoardService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teamBoards")
@RequiredArgsConstructor
public class TeamBoardController {

  private final TeamBoardService teamBoardService;
  private final UserRepository userRepository;

  @PostMapping()
  public ResponseEntity<UUID> saveTeamBoard(
      @RequestBody TeamBoardSaveDto dto,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    long userID = userDetails.getUser().getId();
    User user = userRepository.findById(userID);
    UUID streamerId = user.getStreamer().getId();
    UUID savedId = teamBoardService.save(dto, streamerId).getId();
    return ResponseEntity.ok(savedId);
  }

  @GetMapping("/{teamBoardId}")
  public ResponseEntity<TeamBoardDto> findTeamBoardById(@PathVariable UUID teamBoardId) {
    TeamBoard teamBoard = teamBoardService.findById(teamBoardId);
    return ResponseEntity.ok(new TeamBoardDto(teamBoard));
  }
  @GetMapping("/me")
  public ResponseEntity<List<TeamBoardDto>> findMyTeamBoards(@AuthenticationPrincipal CustomUserDetails userDetails) {
    long userID = userDetails.getUser().getId();
    User user = userRepository.findById(userID);
    UUID streamerId = user.getStreamer().getId();
    List<TeamBoard> teamBoards = teamBoardService.findByStreamerId(streamerId);
    return ResponseEntity.ok(teamBoards.stream().map(TeamBoardDto::new).toList());
  }

  @GetMapping("/streamer/{streamerId}/names")
  public ResponseEntity<List<UUID>> findAllTeamBoardNames(@PathVariable UUID streamerId) {
    List<TeamBoard> list = teamBoardService.findByStreamerId(streamerId);
    return ResponseEntity.ok(list.stream().map(TeamBoard::getId).toList());
  }

  @GetMapping("/streamer/{streamerId}")
  public ResponseEntity<List<TeamBoardDto>> findAllTeamBoards(@PathVariable UUID streamerId) {
    List<TeamBoard> list = teamBoardService.findByStreamerId(streamerId);
    return ResponseEntity.ok(list.stream().map(TeamBoardDto::new).toList());
  }

  @PutMapping("/{teamBoardId}")
  public ResponseEntity<Void> updateTeamBoard(@PathVariable UUID teamBoardId, @RequestBody TeamBoardUpdateDto teamBoardUpdateDto) {
    teamBoardUpdateDto.setTeamBoardId(teamBoardId);
    teamBoardService.updateTeamBoard(teamBoardUpdateDto);
    return ResponseEntity.ok().build();
  }


  @DeleteMapping("/{teamBoardId}")
  public ResponseEntity<Void> deleteTeamBoard(@PathVariable UUID teamBoardId) {
    teamBoardService.deleteTeamBoard(teamBoardId);
    return ResponseEntity.noContent().build();
  }

}
