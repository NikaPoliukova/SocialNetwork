package org.example.controller;


import org.example.entity.Message;
import org.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

  private final MessageService messageService;

  @Autowired
  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @PostMapping("/send")
  public ResponseEntity<Message> sendMessage(@RequestParam Long senderId,
                                             @RequestParam Long receiverId,
                                             @RequestParam String content) {
    Optional<Message> sentMessage = messageService.sendMessage(senderId, receiverId, content);
    return sentMessage.map(message -> ResponseEntity.status(HttpStatus.CREATED).body(message))
        .orElseGet(() -> ResponseEntity.badRequest().build());
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Long userId) {
    List<Message> messages = messageService.getMessagesByUser(userId);
    return ResponseEntity.ok(messages);
  }
}

