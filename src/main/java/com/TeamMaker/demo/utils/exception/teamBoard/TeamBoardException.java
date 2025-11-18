package com.TeamMaker.demo.utils.exception.teamBoard;

import com.TeamMaker.demo.utils.exception.ErrorCode;
import com.TeamMaker.demo.utils.exception.TeamMakerException;
import java.util.Map;

public class TeamBoardException extends TeamMakerException {

  public TeamBoardException(ErrorCode errorCode) {
    super(errorCode);
  }

  public TeamBoardException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
  public TeamBoardException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}
