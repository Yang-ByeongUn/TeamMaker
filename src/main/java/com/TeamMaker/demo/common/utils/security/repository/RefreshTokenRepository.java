package com.TeamMaker.demo.common.utils.security.repository;

import com.TeamMaker.demo.common.utils.security.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByRefreshToken(String refreshToken);

  void deleteByUserId(Long userId);

}
