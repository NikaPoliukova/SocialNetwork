package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
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
