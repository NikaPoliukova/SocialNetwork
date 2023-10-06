package org.example.service;


import org.example.entity.Post;

import java.util.List;

public interface PostService {
  List<Post> getAllPosts();

  List<Post> getPostsByUser(Long userId);

  Post createPost(Long userId, String content);

  Post updatePost(Long postId, String content);

  void deletePost(Long postId);
}