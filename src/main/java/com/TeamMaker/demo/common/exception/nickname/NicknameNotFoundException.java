package com.TeamMaker.demo.common.exception.nickname;

import com.TeamMaker.demo.common.exception.ErrorCode;
import com.TeamMaker.demo.common.exception.TeamMakerException;
import java.util.Map;

public class NicknameNotFoundException extends TeamMakerException {

  public NicknameNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

  public NicknameNotFoundException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
  public NicknameNotFoundException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}