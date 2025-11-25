package com.TeamMaker.demo.common.exception.refreshToken;

import com.TeamMaker.demo.common.exception.ErrorCode;
import java.util.Map;

public class RefreshTokenNotFoundException extends RefreshTokenException {

  public RefreshTokenNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

  public RefreshTokenNotFoundException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  public RefreshTokenNotFoundException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}
