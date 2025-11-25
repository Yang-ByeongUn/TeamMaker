package com.TeamMaker.demo.common.exception.refreshToken;

import com.TeamMaker.demo.common.exception.ErrorCode;
import java.util.Map;

public class RefreshTokenIsExpired extends RefreshTokenException {

  public RefreshTokenIsExpired(ErrorCode errorCode) {
    super(errorCode);
  }

  public RefreshTokenIsExpired(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }

  public RefreshTokenIsExpired(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}
