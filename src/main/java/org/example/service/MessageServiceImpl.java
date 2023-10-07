package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.entity.Message;
import org.example.entity.User;
import org.example.repository.MessageRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public Optional<Message> sendMessage(Long senderId, Long receiverId, String content) {
    User sender = userRepository.findById(senderId).orElse(null);
    User receiver = userRepository.findById(receiverId).orElse(null);

    if (sender != null && receiver != null) {
      Message message = new Message();
      message.setSender(sender);
      message.setReceiver(receiver);
      message.setContent(content);
      message.setTimestamp(new Date());
      return Optional.of(messageRepository.save(message));
    }
    return Optional.empty();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Message> getMessagesByUser(Long userId) {
    User user = userRepository.findById(userId).orElse(null);
    if (user != null) {
      return messageRepository.findBySenderOrReceiver(user, user);
    }
    return new ArrayList<>();
  }
}
