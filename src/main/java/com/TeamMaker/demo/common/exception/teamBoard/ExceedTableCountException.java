package com.TeamMaker.demo.common.exception.teamBoard;

import com.TeamMaker.demo.common.exception.ErrorCode;
import java.util.Map;

public class ExceedTableCountException extends TeamBoardException {

  public ExceedTableCountException(ErrorCode errorCode) {
    super(errorCode);
  }

  public ExceedTableCountException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
  public ExceedTableCountException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}