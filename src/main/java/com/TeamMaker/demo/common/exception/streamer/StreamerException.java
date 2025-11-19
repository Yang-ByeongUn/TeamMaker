package com.TeamMaker.demo.common.exception.streamer;

import com.TeamMaker.demo.common.exception.ErrorCode;
import com.TeamMaker.demo.common.exception.TeamMakerException;
import java.util.Map;

public class StreamerException extends TeamMakerException {

  public StreamerException(ErrorCode errorCode) {
    super(errorCode);
  }

  public StreamerException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
  public StreamerException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}
