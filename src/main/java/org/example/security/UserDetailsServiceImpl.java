package com.example.tinywiny.security;


import com.example.tinywiny.model.User;
import com.example.tinywiny.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
    final User user = userRepository.getUserByUserName(userName);
    return new PrincipalUser(user.getUserName(), user.getPassword(),
        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())), user.getUserId());
  }
}
