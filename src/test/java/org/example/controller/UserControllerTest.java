package org.example.controller;

import org.example.entity.User;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;


public class UserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  void testGetAllUsers() throws Exception {
    User user1 = new User();
    User user2 = new User();
    when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
  }

  @Test
  void testGetUserById() throws Exception {
    Long userId = 1L;
    User user = new User();
    user.setId(userId);
    when(userService.getUserById(userId)).thenReturn(user);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId));
  }

  @Test
  void testDeleteUser() throws Exception {
    Long userId = 1L;
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", userId))
        .andExpect(MockMvcResultMatchers.status().isOk());
    verify(userService, times(1)).deleteUser(userId);
  }

  @Test
  void testGetUserById_InvalidIdFormat() throws Exception {
    String invalidUserId = "invalidId";
    when(userService.getUserById(anyLong())).thenThrow(new IllegalArgumentException());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", invalidUserId))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }
}
