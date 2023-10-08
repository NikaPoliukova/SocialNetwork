package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.UserFollowing;
import org.example.service.UserFollowingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-followings")
public class UserFollowingController {
  private final UserFollowingService userFollowingService;

  @GetMapping
  public List<UserFollowing> getAllUserFollowings() {
    return userFollowingService.getAllUserFollowings();
  }

  @GetMapping("/{id}")
  public Optional<UserFollowing> getUserFollowingById(@PathVariable Long id) {
    return userFollowingService.getUserFollowingById(id);
  }

  @PostMapping
  public UserFollowing createUserFollowing(@RequestBody UserFollowing userFollowing) {
    return userFollowingService.createUserFollowing(userFollowing);
  }

  @DeleteMapping("/{id}")
  public void deleteUserFollowing(@PathVariable Long id) {
    userFollowingService.deleteUserFollowing(id);
  }
}