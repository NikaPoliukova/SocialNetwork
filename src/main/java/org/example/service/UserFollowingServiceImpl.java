package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.UserFollowing;
import org.example.repository.UserFollowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserFollowingServiceImpl implements UserFollowingService {
  private final UserFollowingRepository userFollowingRepository;

  public List<UserFollowing> getAllUserFollowings() {
    return userFollowingRepository.findAll();
  }

  public Optional<UserFollowing> getUserFollowingById(Long id) {
    return userFollowingRepository.findById(id);
  }

  public UserFollowing createUserFollowing(UserFollowing userFollowing) {
    return userFollowingRepository.save(userFollowing);
  }

  public void deleteUserFollowing(Long id) {
    userFollowingRepository.deleteById(id);
  }
}
