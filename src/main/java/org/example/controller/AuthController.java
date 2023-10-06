package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.CredentialsDto;
import org.example.entity.User;
import org.example.security.jwt.JwtUtils;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

  private final JwtUtils jwtUtils;
  private final UserService userService;
  private static final String TOKEN_NAME = "JWT";
  private static final String REFRESH_TOKEN_NAME = "JWT-REFRESH";
  private static final long EXPIRATION = Duration.ofHours(3).toSeconds();

  @PostMapping("/login")
  public void login(@RequestBody CredentialsDto request, HttpServletResponse response) {
    User user = userService.getUserByUserNameAndPassword(request.getUsername(), request.getPassword());
    if (user != null) {
      String token = jwtUtils.generateAccessToken(user);
      String refreshToken = jwtUtils.generateRefreshToken(user.getUsername());
      final Cookie cookieAccess = new Cookie(TOKEN_NAME, token);
      final Cookie cookieRefresh = new Cookie(REFRESH_TOKEN_NAME, refreshToken);
      Arrays.asList(cookieRefresh, cookieAccess)          .forEach(cookie -> {
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) EXPIRATION);
          });
      response.addCookie(cookieAccess);
      response.addCookie(cookieRefresh);
      response.setStatus(HttpStatus.OK.value());
    } else {
      response.setStatus(HttpStatus.CONFLICT.value());
    }

  }
}
