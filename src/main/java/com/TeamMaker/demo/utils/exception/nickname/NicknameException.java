package com.TeamMaker.demo.utils.exception.nickname;

import com.TeamMaker.demo.utils.exception.ErrorCode;
import com.TeamMaker.demo.utils.exception.TeamMakerException;
import java.util.Map;

public class NicknameException extends TeamMakerException {

  public NicknameException(ErrorCode errorCode) {
    super(errorCode);
  }

  public NicknameException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
  public NicknameException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}
