package com.TeamMaker.demo.common.exception.user;

import com.TeamMaker.demo.common.exception.ErrorCode;
import java.util.Map;

public class UserNotFoundException extends UserException {

  public UserNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

  public UserNotFoundException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  public UserNotFoundException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}
