package com.TeamMaker.demo.controller;

import com.TeamMaker.demo.dto.NicknameDto;
import com.TeamMaker.demo.dto.StreamerDto;
import com.TeamMaker.demo.dto.StreamerSaveDto;
import com.TeamMaker.demo.entity.Nickname;
import com.TeamMaker.demo.entity.Position;
import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.repository.Direction;
import com.TeamMaker.demo.repository.streamer.CursorPageResponse;
import com.TeamMaker.demo.service.NicknameService;
import com.TeamMaker.demo.service.StreamerService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/streamers")
@RequiredArgsConstructor
public class StreamerController {

  private final StreamerService streamerService;
  private final NicknameService nicknameService;

  @PostMapping("/save")
  public ResponseEntity<Void> save(@RequestBody StreamerDto streamerDto) {
    streamerService.save(new StreamerSaveDto(streamerDto.getScore(), streamerDto.streamerName, streamerDto.getPosition(), LocalDateTime.now()));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{streamerId}")
  public ResponseEntity<StreamerDto> findById(@PathVariable UUID streamerId) {
    Streamer streamer = streamerService.findById(streamerId);
    return ResponseEntity.ok(new StreamerDto(streamer));
  }

  @GetMapping
  public ResponseEntity<CursorPageResponse<StreamerDto>> findAll(@RequestParam(required = false) String name,
      @RequestParam(defaultValue = "ASC") Direction nameDirection, @RequestParam(required = false) Double fromScore,
      @RequestParam(required = false) Double endScore, @RequestParam(defaultValue = "ASC") Direction scoreDirection,
      @RequestParam(required = false) Position position, @RequestParam(required = false) String cursorName,
      @RequestParam(required = false) Double cursorScore, @RequestParam(required = false) UUID cursorId,
      @RequestParam(defaultValue = "20") int size) {
    CursorPageResponse<StreamerDto> streamerWithCursor = streamerService.findStreamerWithCursor(name, nameDirection, fromScore, endScore,
        scoreDirection, position, cursorId, cursorScore, cursorName, size);

    return ResponseEntity.ok(streamerWithCursor);
  }

  @GetMapping("/{streamerId}/nicknames")
  public ResponseEntity<List<NicknameDto>> findByStreamer(@PathVariable UUID streamerId) {
    List<Nickname> nicknames = nicknameService.findNicknamesByConditions(streamerId, null, null);
    return ResponseEntity.ok(nicknames.stream().map(NicknameDto::new).toList());
  }


  @PostMapping("/{streamerId}")
  public ResponseEntity<Void> change(@PathVariable UUID streamerId, @RequestBody StreamerDto streamerDto) {
    streamerService.changeStreamerDetails(streamerId, streamerDto);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{streamerId}")
  public ResponseEntity<Void> delete(@PathVariable UUID streamerId) {
    streamerService.deleteStreamer(streamerId);
    return ResponseEntity.noContent().build();
  }
}
