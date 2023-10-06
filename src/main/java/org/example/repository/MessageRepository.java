package com.example.SocialMediaApplication.repository;

import com.example.SocialMediaApplication.entity.Message;
import com.example.SocialMediaApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  List<Message> findBySenderOrReceiver(User sender, User receiver);
}