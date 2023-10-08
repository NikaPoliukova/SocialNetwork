package org.example.service;

import org.example.converter.UserConverter;
import org.example.dto.CredentialsDto;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

  @Test
  void testCreateUser_UserAlreadyExists() {
    User existingUser = new User();
    existingUser.setUsername("testUser");
    when(userRepository.findByUsername("testUser")).thenReturn(existingUser);
    CredentialsDto credentialsDto = new CredentialsDto();
    credentialsDto.setUsername("testUser");
    assertThrows(RuntimeException.class, () -> {
      userServiceImpl.createUser(credentialsDto);
    });
  }

  @Test
  void testCreateUser_SuccessfulCreation() {
    when(userRepository.findByUsername("newUser")).thenReturn(null);
    CredentialsDto credentialsDto = new CredentialsDto();
    credentialsDto.setUsername("newUser");
    credentialsDto.setPassword("password");
    User newUser = new User();
    when(userConverter.toUser(credentialsDto)).thenReturn(newUser);
    when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
    when(userRepository.save(newUser)).thenReturn(newUser);
    User result = userServiceImpl.createUser(credentialsDto);
    assertNotNull(result);
    assertEquals("newUser", credentialsDto.getUsername());
    assertEquals("hashedPassword", result.getPassword());
    verify(userRepository, times(1)).save(newUser);
  }

  @Test
  void testUpdateUser_UserNotFound() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(UserNotFoundException.class, () -> {
      userServiceImpl.updateUser(1L, new User());
    });
  }
  @Test
  void testDeleteUser_ValidId() {
    Long userIdToDelete = 1L;
    userServiceImpl.deleteUser(userIdToDelete);
    verify(userRepository, times(1)).deleteById(userIdToDelete);
  }
  @Test
  void testLoginUser_ValidCredentials() {
    User fakeUser = new User();
    fakeUser.setUsername("testUser");
    fakeUser.setPassword("hashedPassword");
    when(userRepository.findByUsername("testUser")).thenReturn(fakeUser);
    when(passwordEncoder.matches("password", "hashedPassword")).thenReturn(true);
    Optional<User> result = userServiceImpl.loginUser("testUser", "password");
    assertThat(result).isPresent().contains(fakeUser);
  }

  @Test
  void testLoginUser_InvalidCredentials() {
    User fakeUser = new User();
    fakeUser.setUsername("testUser");
    fakeUser.setPassword("hashedPassword");
    when(userRepository.findByUsername("testUser")).thenReturn(fakeUser);
    when(passwordEncoder.matches("incorrectPassword", "hashedPassword")).thenReturn(false);
    Optional<User> result = userServiceImpl.loginUser("testUser", "incorrectPassword");
    assertThat(result).isEmpty();
  }

  @Test
  void testLoginUser_UserNotFound() {
    when(userRepository.findByUsername("nonExistentUser")).thenReturn(null);
    Optional<User> result = userServiceImpl.loginUser("nonExistentUser", "password");
    assertThat(result).isEmpty();
  }
}
