package com.TeamMaker.demo.common.utils.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refresh_tokens")
@Getter
@NoArgsConstructor
public class RefreshToken {
  @Id @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false, unique = true)
  private String refreshToken;

  @Column(nullable = false)
  private LocalDateTime expiryDate;

  public RefreshToken(Long id, String refreshToken, LocalDateTime expiryDate) {
    this.userId = id;
    this.refreshToken = refreshToken;
    this.expiryDate = expiryDate;
  }
  public void updateToken(String refreshToken, LocalDateTime expiryDate) {
    this.refreshToken = refreshToken;
    this.expiryDate = expiryDate;
  }
}
