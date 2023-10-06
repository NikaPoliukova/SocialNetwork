package com.example.SocialMediaApplication.controller;

import com.example.SocialMediaApplication.entity.Post;
import com.example.SocialMediaApplication.exception.UserNotFoundException;
import com.example.SocialMediaApplication.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("/")
  public List<Post> getAllPosts() {
    return postService.getAllPosts();
  }

  @GetMapping("/user/{userId}")
  public List<Post> getPostsByUser(@PathVariable Long userId) {
    return postService.getPostsByUser(userId);
  }

  @PostMapping("/user/{userId}")
  public Post createPost(@PathVariable Long userId, @RequestBody String content) throws UserNotFoundException {
    return postService.createPost(userId, content);
  }

  @PutMapping("/{postId}")
  public Post updatePost(@PathVariable Long postId, @RequestBody String content) {
    return postService.updatePost(postId, content);
  }

  @DeleteMapping("/{postId}")
  public void deletePost(@PathVariable Long postId) {
    postService.deletePost(postId);
  }
}
