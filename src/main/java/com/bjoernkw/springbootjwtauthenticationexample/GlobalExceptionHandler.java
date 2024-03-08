package com.bjoernkw.springbootjwtauthenticationexample;

import java.net.URI;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AuthenticationException.class)
  ProblemDetail handleAuthenticationException(AuthenticationException e) {
    ProblemDetail problemDetail = ProblemDetail
        .forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    problemDetail.setTitle("Authentication Error");
    problemDetail.setType(URI.create("http://localhost:8080/authentication_error.html"));
    problemDetail.setProperty("errorCategory", "Authentication");
    problemDetail.setProperty("timestamp", Instant.now());

    return problemDetail;
  }
}
