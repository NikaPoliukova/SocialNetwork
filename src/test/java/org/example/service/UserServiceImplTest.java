package org.example.service;

import org.example.converter.UserConverter;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
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

  @Test
  void testGetUserByUserName_ValidUser() {
    User fakeUser = new User();
    fakeUser.setUsername("testUser");
    when(userRepository.findByUsername("testUser")).thenReturn(fakeUser);
    User result = userServiceImpl.getUserByUserName("testUser");
    assertNotNull(result);
    assertEquals(fakeUser, result);
  }

  @Test
  void testGetUserByUserName_UserNotFound() {
    when(userRepository.findByUsername("nonExistentUser")).thenReturn(null);
    assertThrows(UserNotFoundException.class, () -> {
      userServiceImpl.getUserByUserName("nonExistentUser");
    });
  }

  @Test
  void testGetAllUsers() {
    User user1 = new User();
    user1.setId(1L);
    user1.setUsername("user1");
    User user2 = new User();
    user2.setId(2L);
    user2.setUsername("user2");
    List<User> fakeUsers = Arrays.asList(user1, user2);
    when(userRepository.findAll()).thenReturn(fakeUsers);
    List<User> result = userServiceImpl.getAllUsers();
    assertNotNull(result);
    assertEquals(fakeUsers, result);
  }

  @Test
  void testGetUserById_UserExists() {
    User fakeUser = new User();
    fakeUser.setId(1L);
    when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(fakeUser));
    User result = userServiceImpl.getUserById(1L);
    assertNotNull(result);
    assertEquals(fakeUser, result);
  }

  @Test
  void testGetUserById_UserNotFound() {
    when(userRepository.findById(2L)).thenReturn(java.util.Optional.empty());
    User result = userServiceImpl.getUserById(2L);
    assertNull(result);
  }
}
