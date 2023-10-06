package org.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", unique = true, nullable = false, length = 255)
  private String username;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Column(name = "email", length = 255)
  private String email;

  @OneToMany(mappedBy = "user")
  @JsonIgnore
  private List<Post> posts = new ArrayList<>();

  @OneToMany(mappedBy = "sender")
  @JsonIgnore
  private List<Message> sentMessages = new ArrayList<>();

  @OneToMany(mappedBy = "receiver")
  @JsonIgnore
  private List<Message> receivedMessages = new ArrayList<>();
  @OneToMany
  @JsonIgnore
  @JoinTable(name = "user_following", joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "following_id"))
  private List<User> following;
}
