package org.example.service;

import org.example.entity.UserFollowing;
import org.example.repository.UserFollowingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class UserFollowingServiceImplTest {
  @Mock
  private UserFollowingRepository userFollowingRepository;

  @InjectMocks
  private UserFollowingServiceImpl userFollowingServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testGetAllUserFollowings() {
    List<UserFollowing> fakeList = new ArrayList<>();
    when(userFollowingRepository.findAll()).thenReturn(fakeList);
    List<UserFollowing> result = userFollowingServiceImpl.getAllUserFollowings();
    assertNotNull(result);
    assertEquals(fakeList, result);
  }

  @Test
  void testGetUserFollowingById_UserFollowingExists() {
    Long id = 1L;
    UserFollowing fakeUserFollowing = new UserFollowing();
    fakeUserFollowing.setFollowingId(id);
    when(userFollowingRepository.findById(id)).thenReturn(Optional.of(fakeUserFollowing));
    Optional<UserFollowing> result = userFollowingServiceImpl.getUserFollowingById(id);
    assertTrue(result.isPresent());
    assertEquals(fakeUserFollowing, result.get());
  }

  @Test
  void testGetUserFollowingById_UserFollowingNotFound() {
    Long id = 1L;
    when(userFollowingRepository.findById(id)).thenReturn(Optional.empty());
    Optional<UserFollowing> result = userFollowingServiceImpl.getUserFollowingById(id);
    assertFalse(result.isPresent());
  }

  @Test
  void testCreateUserFollowing() {
    UserFollowing userFollowing = new UserFollowing();
    when(userFollowingRepository.save(userFollowing)).thenReturn(userFollowing);
    UserFollowing result = userFollowingServiceImpl.createUserFollowing(userFollowing);
    assertNotNull(result);
    assertEquals(userFollowing, result);
  }

  @Test
  void testDeleteUserFollowing() {
    Long id = 1L;
    userFollowingServiceImpl.deleteUserFollowing(id);
    verify(userFollowingRepository, times(1)).deleteById(id);
  }
}