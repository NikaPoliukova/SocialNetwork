package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.converter.UserConverter;
import org.example.dto.CredentialsDto;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationRestController {

  private final UserService userService;
  private final UserConverter userConverter;

  @PostMapping
  protected CredentialsDto createUser(@RequestBody CredentialsDto credentialsDto) {
    return userConverter.toDto(userService.createUser(credentialsDto));
  }
}


