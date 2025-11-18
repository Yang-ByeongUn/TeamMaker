package com.TeamMaker.demo.utils.exception.teamBoard;

import com.TeamMaker.demo.utils.exception.ErrorCode;
import java.util.Map;

public class TeamBoardNotFoundException extends TeamBoardException {

  public TeamBoardNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

  public TeamBoardNotFoundException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
  public TeamBoardNotFoundException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}