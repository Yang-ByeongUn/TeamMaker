package com.TeamMaker.demo.common.utils.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

  private final Key key;
  private final long tokenValidityInMilliseconds;
  private final long refreshTokenValidityInMilliseconds;

  public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
      @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds,
      @Value("${jwt.refresh-token-validity-seconds}") long refreshTokenValiditySeconds) {
    this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    this.tokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000;
    this.refreshTokenValidityInMilliseconds = refreshTokenValiditySeconds * 1000;
  }

  //토큰 생성
  public String createAccessToken(String username) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + tokenValidityInMilliseconds);
    return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(validity).signWith(key, SignatureAlgorithm.HS256).compact();
  }

  public String createRefreshToken(String username) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + refreshTokenValidityInMilliseconds);
    return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expiry).signWith(key, SignatureAlgorithm.HS256).compact();
  }

  //토큰에서 유저네임 추출
  public String getUsername(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
  }

  //토큰 유효성 검사
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      log.error("Invalid JWT token : {}", e.getMessage());
    }
    return false;
  }

  public LocalDateTime getExpiry(String token) {
    Date exp = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
    return exp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }
}
