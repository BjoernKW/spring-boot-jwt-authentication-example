package com.bjoernkw.springbootjwtauthenticationexample.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

  private static final String USERNAME_CLAIM = "username";

  @Value("${com.bjoernkw.spring-boot-jwt-authentication-example.jwt-secret}")
  private String jwtSecret;

  @Value("${com.bjoernkw.spring-boot-jwt-authentication-example.jwt-expiration}")
  private int jwtExpiration;

  public String generateJwtToken(Authentication authentication) {
    UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

    return Jwts.builder()
        .subject(userPrincipal.getUsername())
        .claim(USERNAME_CLAIM, userPrincipal.getUsername())
        .issuedAt(new Date())
        .expiration(new Date((new Date()).getTime() + jwtExpiration))
        .signWith(key())
        .compact();
  }

  private SecretKey key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts
        .parser()
        .verifyWith(key())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get(USERNAME_CLAIM, String.class);
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts
          .parser()
          .verifyWith(key())
          .build()
          .parse(authToken);

      return true;
    } catch (MalformedJwtException e) {
      LOGGER.error("Invalid JWT: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      LOGGER.error("JWT has expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      LOGGER.error("JWT is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      LOGGER.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}
