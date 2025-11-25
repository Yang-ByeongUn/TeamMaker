package com.TeamMaker.demo.common.exception.user;

import com.TeamMaker.demo.common.exception.ErrorCode;
import java.util.Map;

public class AlreadyExistUserException  extends UserException {

  public AlreadyExistUserException(ErrorCode errorCode) {
    super(errorCode);
  }

  public AlreadyExistUserException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  public AlreadyExistUserException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}
