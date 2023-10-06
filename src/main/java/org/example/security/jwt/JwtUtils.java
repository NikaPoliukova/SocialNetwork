package org.example.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Component
public class JwtUtils {

  @Value("${security.secretKey}")
  private String secretKey;
  @Value("${security.refresh_secret}")
  private static String jwtRefreshSecret;

  public String generateAccessToken(User user) {
    Date expirationDate = generateExpirationDate(50);
    return Jwts.builder()
        .setSubject(user.getUsername())
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .claim("userId", user.getId())
        .compact();
  }

  public String generateRefreshToken(String login) {
    Date expirationDate = generateExpirationDate(5000);
    return Jwts.builder()
        .setSubject(login)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  public String refreshAccessToken(String expiredToken) {
    Claims claims;
    try {
      claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(expiredToken).getBody();
    } catch (ExpiredJwtException e) {
      claims = e.getClaims();
    }
    Date expirationDate = generateExpirationDate(50);
    return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  public boolean validateToken(String token, String secret) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
    }
    return false;
  }


  public boolean validateAccessToken(String accessToken, String secret) {
    return validateToken(accessToken, secret);
  }

  public boolean validateRefreshToken(String refreshToken, String secret) {
    return validateToken(refreshToken, secret);
  }

  private String getLoginFromToken(String token, String secret) {
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  public String getLoginFromAccessToken(String token) {
    return getLoginFromToken(token, secretKey);
  }

  public boolean isTokenExpired(String token) {
    Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public Date getExpirationDateFromToken(String token) {
    Claims claims;
    try {
      claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      claims = e.getClaims();
    }
    return claims.getExpiration();
  }

  private Date generateExpirationDate(int min) {
    LocalDateTime now = LocalDateTime.now();
    Instant accessExpirationInstant = now.plusMinutes(min).atZone(ZoneId.systemDefault()).toInstant();
    return Date.from(accessExpirationInstant);
  }

  public Claims getTokenClaims(final String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  public String getLoginFromRefreshToken(String token) {
    return getLoginFromToken(token, secretKey);
  }
}
