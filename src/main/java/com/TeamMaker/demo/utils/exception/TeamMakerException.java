package com.TeamMaker.demo.utils.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class TeamMakerException extends RuntimeException {

  private final Map<String, Object> details = new HashMap<>();
  private final ErrorCode errorCode;

  public TeamMakerException(ErrorCode errorCode) {
    super(errorCode.getMessage(), null, false, false);
    this.errorCode = errorCode;
  }

  public TeamMakerException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
  }
  public TeamMakerException(ErrorCode errorCode, Map<String, Object> details){
    this.errorCode = errorCode;
    this.details.putAll(details);
  }
}
