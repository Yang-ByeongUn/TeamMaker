package com.TeamMaker.demo.common.exception.user;

import com.TeamMaker.demo.common.exception.ErrorCode;
import com.TeamMaker.demo.common.exception.TeamMakerException;
import java.util.Map;

public class UserException extends TeamMakerException {

  public UserException(ErrorCode errorCode) {
    super(errorCode);
  }

  public UserException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
  public UserException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}
