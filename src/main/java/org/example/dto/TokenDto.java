package org.example.dto;

import lombok.Data;

@Data
public class TokenDto {
  String access_token;
  String refreshToken;
}
