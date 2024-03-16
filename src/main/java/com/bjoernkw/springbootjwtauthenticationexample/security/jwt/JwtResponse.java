package com.bjoernkw.springbootjwtauthenticationexample.security.jwt;

import java.util.List;

public record JwtResponse(
    String token,
    String username,
    List<String> roles
) {

}
