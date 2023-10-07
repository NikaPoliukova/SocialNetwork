package org.example.service;

import org.example.converter.UserConverter;
import org.example.dto.CredentialsDto;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private HashPassService hashPassService;

  @Mock
  private UserConverter userConverter;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testGetUserByUserNameAndPassword() {
    User user = new User();
    user.setUsername("testUser");
    user.setPassword("hashedPassword");
    when(userRepository.findByUsername("testUser")).thenReturn(user);
    when(hashPassService.verify("password", "hashedPassword")).thenReturn(true);
    User result = userService.getUserByUserNameAndPassword("testUser", "password");
    assertNotNull(result);
    assertEquals("testUser", result.getUsername());
  }

  @Test
  void testGetUserByUserNameAndPasswordWithIncorrectPassword() {
    User user = new User();
    user.setUsername("testUser");
    user.setPassword("hashedPassword");
    when(userRepository.findByUsername("testUser")).thenReturn(user);
    when(hashPassService.verify("wrongPassword", "hashedPassword")).thenReturn(false);
    assertThrows(RuntimeException.class, () -> userService.getUserByUserNameAndPassword("testUser", "wrongPassword"));
  }

  @Test
  void testCreateUser() {
    CredentialsDto credentialsDto = new CredentialsDto("newUser123", "password");
    User newUser = new User();
    newUser.setUsername(credentialsDto.getUsername());
    newUser.setPassword("hashedPassword");
    when(userRepository.findByUsername("newUser123")).thenReturn(null);
    when(userConverter.toUser(credentialsDto)).thenReturn(newUser);
    when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
    User result = userService.createUser(credentialsDto);
    assertNotNull(result);
    assertEquals("newUser123", result.getUsername());
  }

  @Test
  void testCreateUserWithExistingUser() {
    CredentialsDto credentialsDto = new CredentialsDto("existingUser", "password");
    User existingUser = new User();
    when(userRepository.findByUsername("existingUser")).thenReturn(existingUser);
    assertThrows(RuntimeException.class, () -> userService.createUser(credentialsDto));
  }

}