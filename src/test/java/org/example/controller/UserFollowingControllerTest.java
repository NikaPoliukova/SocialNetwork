package org.example.controller;

import org.example.entity.UserFollowing;
import org.example.service.UserFollowingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserFollowingControllerTest {

  @Mock
  private UserFollowingService userFollowingService;

  @InjectMocks
  private UserFollowingController userFollowingController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testGetAllUserFollowings() {
    UserFollowing userFollowing1 = new UserFollowing();
    UserFollowing userFollowing2 = new UserFollowing();
    when(userFollowingService.getAllUserFollowings()).thenReturn(Arrays.asList(userFollowing1, userFollowing2));
    List<UserFollowing> result = userFollowingController.getAllUserFollowings();
    assertNotNull(result);
    assertEquals(2, result.size());
  }

  @Test
  void testGetUserFollowingById() {
    UserFollowing userFollowing = new UserFollowing();
    userFollowing.setFollowingId(1L);
    when(userFollowingService.getUserFollowingById(1L)).thenReturn(Optional.of(userFollowing));
    Optional<UserFollowing> result = userFollowingController.getUserFollowingById(1L);
    assertTrue(result.isPresent());
    assertEquals(userFollowing.getFollowingId(), result.get().getFollowingId());
  }

  @Test
  void testCreateUserFollowing() {
    UserFollowing userFollowing = new UserFollowing();
    when(userFollowingService.createUserFollowing(userFollowing)).thenReturn(userFollowing);
    UserFollowing result = userFollowingController.createUserFollowing(userFollowing);
    assertNotNull(result);
  }

  @Test
  void testDeleteUserFollowing() {
    assertDoesNotThrow(() -> userFollowingController.deleteUserFollowing(1L));
  }
}
