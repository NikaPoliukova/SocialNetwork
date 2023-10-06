package com.example.SocialMediaApplication.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private User sender; // Пользователь, отправивший сообщение

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private User receiver; // Пользователь, получивший сообщение

  @Column(columnDefinition = "TEXT")
  private String content;

  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;
}
