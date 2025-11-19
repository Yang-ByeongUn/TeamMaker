package com.TeamMaker.demo.controller;

import com.TeamMaker.demo.dto.NicknameDto;
import com.TeamMaker.demo.dto.NicknameSaveDto;
import com.TeamMaker.demo.dto.NicknameUpdateDto;
import com.TeamMaker.demo.entity.Nickname;
import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.entity.Tier;
import com.TeamMaker.demo.service.NicknameService;
import com.TeamMaker.demo.service.StreamerService;
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
@RequestMapping("/api/nicknames")
@RequiredArgsConstructor
public class NicknameController {

  private final NicknameService nicknameService;
  private final StreamerService streamerService;

  @PostMapping("/save")
  public ResponseEntity<Void> save(@RequestBody NicknameDto nicknameDto) {
    Streamer streamer = streamerService.findById(nicknameDto.getStreamerId());
    nicknameService.save(new NicknameSaveDto(streamer, nicknameDto.getNickname(), nicknameDto.getTier()));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{nicknameId}")
  public ResponseEntity<NicknameDto> findByNicknameId(@PathVariable UUID nicknameId) {
    return ResponseEntity.ok(new NicknameDto(nicknameService.findById(nicknameId)));
  }

  @GetMapping()
  public ResponseEntity<List<NicknameDto>> findByConditions(
      @RequestParam(required = false) UUID streamerId,
      @RequestParam(required = false) String nickname,
      @RequestParam(required = false) Tier tier) {
    List<Nickname> nicknames = nicknameService.findNicknamesByConditions(streamerId, nickname, tier);
    return ResponseEntity.ok(nicknames.stream().map(NicknameDto::new).toList());
  }

  @PostMapping("/{nicknameId}")
  public ResponseEntity<Void> update(@PathVariable UUID nicknameId, @RequestBody NicknameDto nicknameDto) {
    nicknameService.update(new NicknameUpdateDto(nicknameId, nicknameDto));
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    nicknameService.deleteNickname(id);
    return ResponseEntity.noContent().build();
  }

}
