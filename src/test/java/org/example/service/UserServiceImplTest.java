package org.example.service;

import org.example.converter.UserConverter;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private HashPassService hashPassService;
  @Mock
  private UserConverter userConverter;
  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImpl userServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testGetUserByUserNameAndPassword_ValidCredentials() {
    User user = new User();
    user.setUsername("testUser");
    user.setPassword("hashedPassword");
    when(userRepository.findByUsername("testUser")).thenReturn(user);
    when(hashPassService.verify("password", "hashedPassword")).thenReturn(true);
    User result = userServiceImpl.getUserByUserNameAndPassword("testUser", "password");
    assertNotNull(result);
    assertEquals(user, result);
    verify(userRepository, times(1)).findByUsername(any());
  }

  @Test
  void testGetUserByUserNameAndPassword_InvalidCredentials() {
    User user = new User();
    user.setUsername("testUser");
    user.setPassword("hashedPassword");
    when(userRepository.findByUsername("testUser")).thenReturn(user);
    when(hashPassService.verify("incorrectPassword", "hashedPassword")).thenReturn(false);
    try {
      userServiceImpl.getUserByUserNameAndPassword("testUser", "incorrectPassword");
      fail("Expected RuntimeException was not thrown");
    } catch (RuntimeException ex) {
      assertEquals("enter incorrect password or login", ex.getMessage());
    }
  }

}
