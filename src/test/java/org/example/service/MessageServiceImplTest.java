package org.example.service;

import org.example.entity.Message;
import org.example.entity.User;
import org.example.repository.MessageRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
class MessageServiceImplTest {

  @Mock
  private MessageRepository messageRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private MessageServiceImpl messageServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testSendMessage_ValidUsers() {
    User sender = new User();
    sender.setId(1L);
    User receiver = new User();
    receiver.setId(2L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
    when(userRepository.findById(2L)).thenReturn(Optional.of(receiver));

    String content = "Hello, World!";
    Message message = new Message();
    message.setSender(sender);
    message.setReceiver(receiver);
    message.setContent(content);
    message.setTimestamp(new Date());

    when(messageRepository.save(any(Message.class))).thenReturn(message);

    Optional<Message> result = messageServiceImpl.sendMessage(1L, 2L, content);

    assertTrue(result.isPresent());
    assertEquals(message, result.get());
  }

  @Test
  void testSendMessage_InvalidUsers() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());
    when(userRepository.findById(2L)).thenReturn(Optional.empty());

    String content = "Hello, World!";
    Optional<Message> result = messageServiceImpl.sendMessage(1L, 2L, content);

    assertFalse(result.isPresent());
  }

  @Test
  void testGetMessagesByUser_ValidUser() {
    User user = new User();
    user.setId(1L);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    Message message1 = new Message();
    message1.setSender(user);
    message1.setReceiver(user);
    message1.setContent("Message 1");
    message1.setTimestamp(new Date());

    Message message2 = new Message();
    message2.setSender(user);
    message2.setReceiver(user);
    message2.setContent("Message 2");
    message2.setTimestamp(new Date());

    List<Message> messages = new ArrayList<>();
    messages.add(message1);
    messages.add(message2);

    when(messageRepository.findBySenderOrReceiver(user, user)).thenReturn(messages);

    List<Message> result = messageServiceImpl.getMessagesByUser(1L);

    assertEquals(messages.size(), result.size());
    assertTrue(result.contains(message1));
    assertTrue(result.contains(message2));
  }

  @Test
  void testGetMessagesByUser_InvalidUser() {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    List<Message> result = messageServiceImpl.getMessagesByUser(1L);

    assertTrue(result.isEmpty());
  }
}