package com.example.SocialMediaApplication.service;


import com.example.SocialMediaApplication.entity.User;
import com.example.SocialMediaApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public User getUserById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User updateUser(Long id, User user) {
    User existingUser = userRepository.findById(id).orElse(null);
    if (existingUser != null) {
      existingUser.setUsername(user.getUsername());
      existingUser.setPassword(user.getPassword());
      existingUser.setFullName(user.getFullName());
      existingUser.setEmail(user.getEmail());
      existingUser.setFollowing(user.getFollowing());
      return userRepository.save(existingUser);
    }
    return null;
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  @Transactional
  public boolean followUser(Long followerId, Long followingId) {
    User follower = userRepository.findById(followerId).orElse(null);
    User following = userRepository.findById(followingId).orElse(null);
    if (follower != null && following != null) {
      if (!follower.getFollowing().contains(following)) {
        follower.getFollowing().add(following);
        userRepository.save(follower);
        return true;
      }
    }
    return false;
  }

  @Override
  @Transactional
  public void unfollowUser(Long followerId, Long followingId) {
    User follower = userRepository.findById(followerId).orElse(null);
    User following = userRepository.findById(followingId).orElse(null);
    if (follower != null && following != null) {
      follower.getFollowing().remove(following);
      userRepository.save(follower);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getFollowers(Long userId) {
    User user = userRepository.findById(userId).orElse(null);
    if (user != null) {
      List<User> followers = userRepository.findByFollowersContains(user);
      return followers != null ? followers : Collections.emptyList();
    }
    return Collections.emptyList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> getFollowing(Long userId) {
    Optional<User> userOptional = userRepository.findById(userId);
    return userOptional.map(User::getFollowing).orElse(Collections.emptyList());
  }

  public Optional<User> registerUser(User user) {
    if (userRepository.findByUsername(user.getUsername()) == null) {
      // Хеширование пароля перед сохранением
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      return Optional.of(userRepository.save(user));
    }
    return Optional.empty();
  }

  public Optional<User> loginUser(String username, String password) {
    Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
    return userOptional.filter(user -> passwordEncoder.matches(password, user.getPassword()));
  }
}
