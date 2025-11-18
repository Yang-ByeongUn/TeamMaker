package com.TeamMaker.demo.service;

import com.TeamMaker.demo.dto.NicknameSaveDto;
import com.TeamMaker.demo.entity.Nickname;
import com.TeamMaker.demo.entity.Tier;
import com.TeamMaker.demo.repository.nickname.NicknameRepository;
import com.TeamMaker.demo.utils.exception.ErrorCode;
import com.TeamMaker.demo.utils.exception.nickname.NicknameNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NicknameService {

  private final NicknameRepository nicknameRepository;

  @Transactional
  public Nickname save(NicknameSaveDto nicknameSaveDto){
    Nickname nickname = new Nickname(nicknameSaveDto);
    return nicknameRepository.save(nickname);
  }
  public Nickname findById(UUID id){
    return nicknameRepository.findById(id).orElseThrow(()-> new NicknameNotFoundException(ErrorCode.NICKNAME_NOT_FOUND));
  }
  @Transactional(readOnly = true)
  public List<Nickname> findNicknamesByConditions(UUID streamerId, String nickname, Tier tier){
    return nicknameRepository.findNicknamesByConditions(streamerId, nickname, tier);
  }
  public void deleteNickname(UUID nicknameId){
    nicknameRepository.deleteById(nicknameId);
  }
}
