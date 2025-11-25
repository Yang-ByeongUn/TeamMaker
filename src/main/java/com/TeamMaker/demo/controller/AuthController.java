package com.TeamMaker.demo.controller;

import com.TeamMaker.demo.common.exception.ErrorCode;
import com.TeamMaker.demo.common.exception.refreshToken.RefreshTokenIsExpired;
import com.TeamMaker.demo.common.exception.refreshToken.RefreshTokenNotFoundException;
import com.TeamMaker.demo.common.exception.user.AlreadyExistUserException;
import com.TeamMaker.demo.common.exception.user.UserNotFoundException;
import com.TeamMaker.demo.common.utils.security.CustomUserDetails;
import com.TeamMaker.demo.common.utils.security.RefreshToken;
import com.TeamMaker.demo.common.utils.security.Role;
import com.TeamMaker.demo.common.utils.security.User;
import com.TeamMaker.demo.common.utils.security.jwt.JwtTokenProvider;
import com.TeamMaker.demo.common.utils.security.repository.RefreshTokenRepository;
import com.TeamMaker.demo.common.utils.security.repository.UserRepository;
import com.TeamMaker.demo.dto.LoginRequest;
import com.TeamMaker.demo.dto.LoginResponse;
import com.TeamMaker.demo.dto.RefreshRequest;
import com.TeamMaker.demo.dto.SignupRequest;
import com.TeamMaker.demo.dto.StreamerSaveDto;
import com.TeamMaker.demo.dto.TokenResponse;
import com.TeamMaker.demo.entity.Streamer;
import com.TeamMaker.demo.service.StreamerService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final StreamerService streamerService;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    String username = authentication.getName();
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    String accessToken = jwtTokenProvider.createAccessToken(username);
    String refreshToken = jwtTokenProvider.createRefreshToken(username);
    //refreshToken 저장
    LocalDateTime expiryDate = jwtTokenProvider.getExpiry(refreshToken);
    refreshTokenRepository.deleteByUserId(user.getId());
    RefreshToken token = new RefreshToken(user.getId(), refreshToken, expiryDate);
    refreshTokenRepository.save(token);
    return new LoginResponse(accessToken, refreshToken);
  }
  @PostMapping("/signup")
  public void signup(@RequestBody SignupRequest request) {
    User user = new User(
        request.getUsername(),
        passwordEncoder.encode(request.getPassword()),
        Role.ROLE_STREAMER
    );
    Streamer streamer = new Streamer(
        new StreamerSaveDto(
            request.getScore(),
            request.getStreamerName(),
            request.getPosition(),
            LocalDateTime.now()
        )
    );
    // 양방향 연결
    user.connectStreamer(streamer);
    User save = userRepository.save(user);
    System.out.println("user.getId() = " + save.getId());
    System.out.println("streamer.getId() = " + streamer.getId());
  }

  @PostMapping("/refresh")
  public TokenResponse refresh (@RequestBody RefreshRequest request){
    String refreshToken = request.refreshToken();
    RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RefreshTokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
    if( token.getExpiryDate().isBefore(LocalDateTime.now())){
      throw new RefreshTokenIsExpired(ErrorCode.REFRESH_TOKEN_IS_EXPIRED);
    }
    String username = jwtTokenProvider.getUsername(refreshToken);
    String newAccessToken = jwtTokenProvider.createAccessToken(username);
    String newRefreshToken = jwtTokenProvider.createRefreshToken(username);
    token.updateToken(newRefreshToken, jwtTokenProvider.getExpiry(newRefreshToken));
    refreshTokenRepository.save(token);
    return new TokenResponse(newAccessToken, refreshToken);
  }

  @PostMapping("/logout")
  public void logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
    Long userId = userDetails.getUser().getId();
    refreshTokenRepository.deleteByUserId(userId);
  }
}
