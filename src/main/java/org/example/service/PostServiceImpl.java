package com.example.SocialMediaApplication.service;

import com.example.SocialMediaApplication.entity.Post;
import com.example.SocialMediaApplication.entity.User;
import com.example.SocialMediaApplication.exception.PostNotFoundException;
import com.example.SocialMediaApplication.exception.UserNotFoundException;
import com.example.SocialMediaApplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final UserService userService;

  @Autowired
  public PostServiceImpl(PostRepository postRepository, UserService userService) {
    this.postRepository = postRepository;
    this.userService = userService;
  }

  @Override
  public List<Post> getAllPosts() {
    List<Post> posts = postRepository.findAll();
    return posts != null ? posts : Collections.emptyList();
  }

  @Override
  public List<Post> getPostsByUser(Long userId) {
    List<Post> posts = postRepository.findPostsByUserId(userId);
    return posts != null ? posts : Collections.emptyList();
  }

  @Override
  public Post createPost(Long userId, String content) throws UserNotFoundException {
    User user = userService.getUserById(userId);
    if (user != null) {
      Post post = new Post();
      post.setUser(user);
      post.setContent(content);
      post.setTimestamp(new Date());
      return postRepository.save(post);
    }
    throw new UserNotFoundException("User not found with ID: " + userId);
  }

  @Override
  public Post updatePost(Long postId, String content) {
    Post existingPost = postRepository.findById(postId).orElseThrow(() ->
        new PostNotFoundException("Post not found with ID: " + postId));
    existingPost.setContent(content);
    return postRepository.save(existingPost);
  }

  @Override
  public void deletePost(Long postId) {
    postRepository.deleteById(postId);
  }
}
