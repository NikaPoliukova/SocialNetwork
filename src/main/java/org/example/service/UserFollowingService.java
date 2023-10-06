package org.example.service;

import org.example.entity.UserFollowing;

import java.util.List;
import java.util.Optional;

public interface UserFollowingService {
  public List<UserFollowing> getAllUserFollowings();

  public Optional<UserFollowing> getUserFollowingById(Long id);


  public UserFollowing createUserFollowing(UserFollowing userFollowing);


  public void deleteUserFollowing(Long id);
}
