package com.TeamMaker.demo.service;

import com.TeamMaker.demo.common.exception.streamer.StreamerNotFoundException;
import com.TeamMaker.demo.dto.NicknameDto;
import com.TeamMaker.demo.dto.NicknameSaveDto;
import com.TeamMaker.demo.dto.NicknameUpdateDto;
import com.TeamMaker.demo.entity.Nickname;
import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.entity.Tier;
import com.TeamMaker.demo.repository.nickname.NicknameRepository;
import com.TeamMaker.demo.common.exception.ErrorCode;
import com.TeamMaker.demo.common.exception.nickname.NicknameNotFoundException;
import com.TeamMaker.demo.repository.streamer.StreamerRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NicknameService {

  private final NicknameRepository nicknameRepository;
  private final StreamerRepository streamerRepository;

  @Transactional
  public Nickname save(NicknameSaveDto nicknameSaveDto) {
    Nickname nickname = new Nickname(nicknameSaveDto);
    Streamer streamer = nicknameSaveDto.getStreamer();
    streamer.getNicknames().add(nickname);
    return nicknameRepository.save(nickname);
  }

  public Nickname findById(UUID id) {
    return nicknameRepository.findById(id).orElseThrow(() -> new NicknameNotFoundException(ErrorCode.NICKNAME_NOT_FOUND));
  }

  @Transactional(readOnly = true)
  public List<Nickname> findNicknamesByConditions(UUID streamerId, String nickname, Tier tier) {
    return nicknameRepository.findNicknamesByConditions(streamerId, nickname, tier);
  }

  public void update(NicknameUpdateDto nicknameUpdateDto) {
    Nickname nickname = nicknameRepository.findById(nicknameUpdateDto.getNicknameId())
        .orElseThrow(() -> new NicknameNotFoundException(ErrorCode.NICKNAME_NOT_FOUND));
    Streamer streamer = streamerRepository.findById(nicknameUpdateDto.getStreamerId())
        .orElseThrow(() -> new StreamerNotFoundException(ErrorCode.STREAMER_NOT_FOUND));
    nickname.update(streamer, nicknameUpdateDto);
  }

  public void deleteNickname(UUID nicknameId) {
    nicknameRepository.deleteById(nicknameId);
  }
}
