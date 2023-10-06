package com.example.SocialMediaApplication.repository;


import com.example.SocialMediaApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByFollowersContains(User user);

  User findByUsername(String username);
}