package org.example.controller;

import org.example.entity.Post;
import org.example.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class PostControllerTest {

  @Mock
  private PostService postService;

  @InjectMocks
  private PostController postController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
  }

  @Test
  void testGetAllPosts() throws Exception {
    List<Post> posts = Arrays.asList(new Post(), new Post());
    when(postService.getAllPosts()).thenReturn(posts);
    mockMvc.perform(get("/api/posts"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
  }

  @Test
  void testGetPostsByUser() throws Exception {
    Long userId = 1L;
    List<Post> userPosts = Arrays.asList(new Post(), new Post());
    when(postService.getPostsByUser(userId)).thenReturn(userPosts);
    mockMvc.perform(get("/api/posts/user/{userId}", userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
  }

  @Test
  void testCreatePost() throws Exception {
    Long userId = 1L;
    String content = "Post content";
    Post newPost = new Post();
    newPost.setId(1L);
    newPost.setContent(content);
    when(postService.createPost(userId, content)).thenReturn(newPost);
    mockMvc.perform(post("/api/posts/user/{userId}", userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(content));
  }

  @Test
  void testUpdatePost() throws Exception {
    Long postId = 1L;
    String updatedContent = "Updated post content";
    Post updatedPost = new Post();
    updatedPost.setId(postId);
    updatedPost.setContent(updatedContent);
    when(postService.updatePost(postId, updatedContent)).thenReturn(updatedPost);
    mockMvc.perform(put("/api/posts/{postId}", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedContent))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(postId))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(updatedContent));
  }

  @Test
  void testDeletePost() throws Exception {
    Long postId = 1L;
    mockMvc.perform(delete("/api/posts/{postId}", postId))
        .andExpect(MockMvcResultMatchers.status().isOk());
    verify(postService, times(1)).deletePost(postId);
  }
}
