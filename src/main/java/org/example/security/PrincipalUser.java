package com.example.tinywiny.security;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Value
public class PrincipalUser extends User {

  Long userId;

  public PrincipalUser(String userName, String password, Collection<? extends GrantedAuthority> authorities, Long userId) {
    super(userName, password, authorities);
    this.userId = userId;
  }
}