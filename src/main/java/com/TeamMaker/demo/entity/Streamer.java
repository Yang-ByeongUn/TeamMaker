package com.TeamMaker.demo.entity;

import com.TeamMaker.demo.dto.StreamerSaveDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "streamers")
@RequiredArgsConstructor
@Getter
public class Streamer {
  @Id
  private UUID id;
  private double score;
  private String streamerName;
  @Enumerated(EnumType.STRING)
  private Position position;
  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime updatedAt;
  @OneToMany(mappedBy = "nickname")
  private final List<Nickname> nicknames = new ArrayList<>();;
  @OneToMany(mappedBy = "streamer")
  private final List<TeamBoard> teamBoards = new ArrayList<>();;

  public Streamer(StreamerSaveDto userSaveDto) {
    score = userSaveDto.getScore();
    streamerName = userSaveDto.getStreamerName();
    position = userSaveDto.getPosition();
    createdAt = userSaveDto.getCreatedAt();
    updatedAt = userSaveDto.getCreatedAt();
  }
  public void setDetails(Double score, String streamerName, Position position){
    if(score != null ) this.score = score;
    if(streamerName != null ) this.streamerName = streamerName;
    if(position != null ) this.position = position;
  }
}
