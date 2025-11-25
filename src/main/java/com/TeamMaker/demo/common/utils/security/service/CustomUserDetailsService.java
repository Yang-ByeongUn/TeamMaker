package com.TeamMaker.demo.common.utils.security.service;

import com.TeamMaker.demo.common.exception.ErrorCode;
import com.TeamMaker.demo.common.exception.user.UserNotFoundException;
import com.TeamMaker.demo.common.utils.security.CustomUserDetails;
import com.TeamMaker.demo.common.utils.security.User;
import com.TeamMaker.demo.common.utils.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    return new CustomUserDetails(user);
  }
}
