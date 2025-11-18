package com.TeamMaker.demo.utils.exception.streamer;

import com.TeamMaker.demo.utils.exception.ErrorCode;
import java.util.Map;

public class StreamerNotFoundException extends StreamerException {

  public StreamerNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

  public StreamerNotFoundException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
  public StreamerNotFoundException(ErrorCode errorCode, Map<String, Object> details) {
    super(errorCode, details);
  }
}