package org.example.entity;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(columnDefinition = "TEXT")
  private String content;

  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;
  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "user_id")
  private User user;
}
