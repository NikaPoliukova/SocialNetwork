package org.example.service;

import org.example.entity.UserFollowing;
import org.example.repository.UserFollowingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFollowingServiceImplTest {

  @InjectMocks
  private UserFollowingServiceImpl userFollowingService;

  @Mock
  private UserFollowingRepository userFollowingRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetAllUserFollowings() {
    List<UserFollowing> userFollowings = new ArrayList<>();
    UserFollowing userFollowing1 = new UserFollowing();
    userFollowing1.setFollowingId(1L);
    UserFollowing userFollowing2 = new UserFollowing();
    userFollowing2.setFollowingId(2L);
    userFollowings.add(userFollowing1);
    userFollowings.add(userFollowing2);
    when(userFollowingRepository.findAll()).thenReturn(userFollowings);
    List<UserFollowing> result = userFollowingService.getAllUserFollowings();
    assertEquals(2, result.size());
    assertEquals(1L, result.get(0).getFollowingId());
    assertEquals(2L, result.get(1).getFollowingId());
  }

  @Test
  public void testGetUserFollowingById() {
    Long id = 1L;
    UserFollowing userFollowing = new UserFollowing();
    userFollowing.setFollowingId(id);
    when(userFollowingRepository.findById(id)).thenReturn(Optional.of(userFollowing));
    Optional<UserFollowing> result = userFollowingService.getUserFollowingById(id);
    assertTrue(result.isPresent());
    assertEquals(1L, result.get().getFollowingId());
  }

  @Test
  public void testCreateUserFollowing() {
    UserFollowing userFollowing = new UserFollowing();
    userFollowing.setFollowingId(1L);
    when(userFollowingRepository.save(userFollowing)).thenReturn(userFollowing);
    UserFollowing result = userFollowingService.createUserFollowing(userFollowing);
    assertEquals(1L, result.getFollowingId());
  }

  @Test
  public void testDeleteUserFollowing() {
    Long id = 1L;
    userFollowingService.deleteUserFollowing(id);
    verify(userFollowingRepository, times(1)).deleteById(id);
  }
}