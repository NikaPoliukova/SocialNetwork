package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.exception.PostNotFoundException;
import org.example.exception.UserNotFoundException;
import org.example.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
  private final PostRepository postRepository;
  private final UserService userService;


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
  public Post createPost(Long userId, String content) {
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
