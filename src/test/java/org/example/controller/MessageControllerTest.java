package org.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Message;
import org.example.entity.User;
import org.example.service.MessageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MessageControllerTest {

  @Mock
  private MessageService messageService;

  @InjectMocks
  private MessageController messageController;

  private MockMvc mockMvc;

  public MessageControllerTest() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
  }

  @Test
  void testSendMessage() throws Exception {
    User sender = new User();
    sender.setId(1L);
    User receiver = new User();
    receiver.setId(2L);
    String content = "Hello, World!";
    Message sentMessage = new Message();
    sentMessage.setSender(sender);
    sentMessage.setReceiver(receiver);
    sentMessage.setContent(content);
    sentMessage.setTimestamp(new Date());
    when(messageService.sendMessage(sender.getId(), receiver.getId(), content)).thenReturn(Optional.of(sentMessage));
    MvcResult result = mockMvc.perform(post("/api/messages/send")
            .param("senderId", sender.getId().toString())
            .param("receiverId", receiver.getId().toString())
            .param("content", content)
            .contentType(MediaType.APPLICATION_JSON))
        .andReturn();
    assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    String responseContent = result.getResponse().getContentAsString();
    assertNotNull(responseContent);
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(responseContent);
    assertNotNull(jsonNode);
  }


  @Test
  void testSendMessageBadRequest() throws Exception {
    User sender = new User();
    sender.setId(1L);
    User receiver = new User();
    receiver.setId(2L);
    String content = "Hello, World!";
    when(messageService.sendMessage(sender.getId(), receiver.getId(), content)).thenReturn(Optional.empty());
    mockMvc.perform(post("/api/messages/send")
            .param("senderId", sender.getId().toString())
            .param("receiverId", receiver.getId().toString())
            .param("content", content)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetMessagesByUser() throws Exception {
    User user = new User();
    user.setId(1L);
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
    List<Message> messages = Arrays.asList(message1, message2);
    when(messageService.getMessagesByUser(user.getId())).thenReturn(messages);
    mockMvc.perform(get("/api/messages/user/{userId}", user.getId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(messages.size()));
  }
}

