package com.example.SocialMediaApplication.service;


import com.example.SocialMediaApplication.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
  List<User> getAllUsers();

  User getUserById(Long id);

  User createUser(User user);

  User updateUser(Long id, User user);

  void deleteUser(Long id);

  boolean followUser(Long followerId, Long followingId);

  void unfollowUser(Long followerId, Long followingId);

  List<User> getFollowers(Long userId);

  List<User> getFollowing(Long userId);

  public Optional<User> registerUser(User user);

  public Optional<User> loginUser(String username, String password);
}
