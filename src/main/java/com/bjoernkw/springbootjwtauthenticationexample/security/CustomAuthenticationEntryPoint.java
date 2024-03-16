package com.bjoernkw.springbootjwtauthenticationexample.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@RequiredArgsConstructor
class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      CustomAuthenticationEntryPoint.class);

  private final HandlerExceptionResolver handlerExceptionResolver;

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authenticationException
  ) {
    LOGGER.error("Unauthorized error: {}", authenticationException.getMessage());

    handlerExceptionResolver.resolveException(request, response, null, authenticationException);
  }
}
