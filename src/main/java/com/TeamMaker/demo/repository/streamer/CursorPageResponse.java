package com.TeamMaker.demo.repository.streamer;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class CursorPageResponse<T> {
  private List<T> content;
  private String nextCursorName;
  private Double nextCursorScore;
  private UUID nextCursorId;
  private boolean hasNext;

  public CursorPageResponse(
      List<T> content,
      String nextCursorName,
      Double nextCursorScore,
      UUID nextCursorId,
      boolean hasNext
  ) {
    this.content = content;
    this.nextCursorName = nextCursorName;
    this.nextCursorScore = nextCursorScore;
    this.nextCursorId = nextCursorId;
    this.hasNext = hasNext;
  }
}
