package org.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "sender_id", nullable = false)
  private User sender;

  @Column(name = "content", length = 255)
  private String content;

  @Column(name = "timestamp")
  private Date timestamp;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "receiver_id", nullable = false)
  private User receiver;

}
