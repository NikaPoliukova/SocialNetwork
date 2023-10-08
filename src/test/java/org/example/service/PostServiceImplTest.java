package org.example.service;

import org.example.entity.Post;
import org.example.entity.User;
import org.example.exception.PostNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceImplTest {
  @Mock
  private PostRepository postRepository;

  @Mock
  private UserService userService;

  @InjectMocks
  private PostServiceImpl postServiceImpl;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testGetAllPosts() {
    List<Post> fakePosts = new ArrayList<>();
    fakePosts.add(new Post());
    fakePosts.add(new Post());
    when(postRepository.findAll()).thenReturn(fakePosts);
    List<Post> result = postServiceImpl.getAllPosts();
    assertNotNull(result);
    assertEquals(fakePosts, result);
  }

  @Test
  void testGetPostsByUser() {
    Long userId = 1L;
    List<Post> fakePosts = new ArrayList<>();
    fakePosts.add(new Post());
    fakePosts.add(new Post());
    when(postRepository.findPostsByUserId(userId)).thenReturn(fakePosts);
    List<Post> result = postServiceImpl.getPostsByUser(userId);
    assertNotNull(result);
    assertEquals(fakePosts, result);
  }

  @Test
  void testCreatePost() {
    Long userId = 1L;
    User fakeUser = new User();
    when(userService.getUserById(userId)).thenReturn(fakeUser);
    Post fakePost = new Post();
    when(postRepository.save(any(Post.class))).thenReturn(fakePost);
    Post result = postServiceImpl.createPost(userId, "Test content");
    assertNotNull(result);
    assertEquals(fakePost, result);
  }

  @Test
  void testCreatePost_UserNotFound() {
    Long userId = 1L;
    when(userService.getUserById(userId)).thenReturn(null);
    assertThrows(UserNotFoundException.class, () -> {
      postServiceImpl.createPost(userId, "Test content");
    });
  }

  @Test
  void testUpdatePost() {
    Long postId = 1L;
    Post fakePost = new Post();
    when(postRepository.findById(postId)).thenReturn(Optional.of(fakePost));
    when(postRepository.save(any(Post.class))).thenReturn(fakePost);
    Post result = postServiceImpl.updatePost(postId, "Updated content");
    assertNotNull(result);
    assertEquals("Updated content", result.getContent());
  }

  @Test
  void testUpdatePost_PostNotFound() {
    Long postId = 1L;
    when(postRepository.findById(postId)).thenReturn(Optional.empty());
    assertThrows(PostNotFoundException.class, () -> {
      postServiceImpl.updatePost(postId, "Updated content");
    });
  }

  @Test
  void testDeletePost() {
    postServiceImpl.deletePost(1L);
    verify(postRepository, times(1)).deleteById(1L);
  }
}