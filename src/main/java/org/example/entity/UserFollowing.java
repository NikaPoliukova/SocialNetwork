package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user_following")
public class UserFollowing {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "following_id")
  private Long followingId;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "user_id")
  private User user;
}
