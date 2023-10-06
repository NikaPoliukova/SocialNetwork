package com.example.SocialMediaApplication.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user; // Пользователь, который опубликовал пост

  @Column(columnDefinition = "TEXT")
  private String content;

  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;
}
