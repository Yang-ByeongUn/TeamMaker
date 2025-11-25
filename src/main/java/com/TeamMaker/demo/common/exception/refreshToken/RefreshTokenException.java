package com.TeamMaker.demo.common.exception.refreshToken;

import com.TeamMaker.demo.common.exception.ErrorCode;
import com.TeamMaker.demo.common.exception.TeamMakerException;
import java.util.Map;

public class RefreshTokenException extends TeamMakerException {

  public RefreshTokenException(ErrorCode errorCode) {
    super(errorCode);
  }

  public RefreshTokenException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
  public RefreshTokenException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}
