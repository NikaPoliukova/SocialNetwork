package com.example.SocialMediaApplication.controller;

import com.example.SocialMediaApplication.dto.LoginForm;
import com.example.SocialMediaApplication.entity.User;
import com.example.SocialMediaApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserService userService;

  @Autowired
  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<User> registerUser(@RequestBody User user) {
    Optional<User> registeredUser = userService.registerUser(user);
    if (registeredUser.isPresent()) {
      return ResponseEntity.ok(registeredUser.get());
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<User> loginUser(@RequestBody LoginForm loginForm) {
    Optional<User> user = userService.loginUser(loginForm.getUsername(), loginForm.getPassword());
    if (user.isPresent()) {
      return ResponseEntity.ok(user.get());
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
