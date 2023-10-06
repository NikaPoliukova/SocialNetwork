package org.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

  // Подставьте вашу логику загрузки пользователя из базы данных или другого источника данных
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Ваша логика загрузки пользователя по его имени пользователя (username)
    // Возвращайте объект UserDetails, который представляет пользователя, или выбрасывайте UsernameNotFoundException, если пользователь не найден.

    // Пример загрузки пользователя (замените на вашу логику):
    if ("user".equals(username)) {
      return User.builder()
          .username("user")
          .password("{bcrypt}$2a$10$9yL6pSv4IT7woBYb.WJdN.xz3UJkUoJqPDleJee45jK3Ms6HP/3K.") // Замените на хешированный пароль
          .roles("USER")
          .build();
    } else {
      throw new UsernameNotFoundException("Пользователь не найден");
    }
  }
}
