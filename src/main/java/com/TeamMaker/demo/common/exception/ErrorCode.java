package com.TeamMaker.demo.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum  ErrorCode {
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "존재하지 않는 회원입니다."),
  ALREADY_EXIST_USER(HttpStatus.BAD_REQUEST, "USER_002", "이미 존재하는 회원입니다."),
  REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "REFRESH_TOKEN_001", "존재하지 않는 리프레시 토큰입니다."),
  REFRESH_TOKEN_IS_EXPIRED(HttpStatus.NOT_FOUND, "REFRESH_TOKEN_002", "만료된 리프레시 토큰입니다."),
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
