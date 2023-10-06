package org.example.service;



import org.example.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
  Optional<Message> sendMessage(Long senderId, Long receiverId, String content);

  List<Message> getMessagesByUser(Long userId);
}
