package com.TeamMaker.demo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum  ErrorCode {
  STREAMER_NOT_FOUND(HttpStatus.NOT_FOUND, "STREAMER_001", "존재하지 않는 스트리머입니다."),
  NICKNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "NICKNAME_001", "존재하지 않는 닉네임입니다."),
  TEAM_BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "TEAM_BOARD_001", "존재하지 않는 팀보드입니다."),
  EXCEED_TABLE_COUNT(HttpStatus.BAD_REQUEST, "TEAM_BOARD_002", "10개 이상의 테이블을 생성할 수 없습니다.");
  private HttpStatus httpStatus;
  private String code;
  private String message;

  ErrorCode(HttpStatus httpStatus , String code, String message) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
  }
}
