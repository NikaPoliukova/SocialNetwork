package com.example.SocialMediaApplication.controller;

import com.example.SocialMediaApplication.entity.User;
import com.example.SocialMediaApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @PostMapping
  public User createUser(@RequestBody User user) {
    return userService.createUser(user);
  }

  @PutMapping("/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User user) {
    return userService.updateUser(id, user);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
  }

  @PostMapping("/{followerId}/follow/{followingId}")
  public boolean followUser(@PathVariable Long followerId, @PathVariable Long followingId) {
    return userService.followUser(followerId, followingId);
  }

  @PostMapping("/{followerId}/unfollow/{followingId}")
  public void unfollowUser(@PathVariable Long followerId, @PathVariable Long followingId) {
    userService.unfollowUser(followerId, followingId);
  }

  @GetMapping("/{userId}/followers")
  public List<User> getFollowers(@PathVariable Long userId) {
    return userService.getFollowers(userId);
  }

  @GetMapping("/{userId}/following")
  public List<User> getFollowing(@PathVariable Long userId) {
    return userService.getFollowing(userId);
  }
}
