package com.bjoernkw.springbootjwtauthenticationexample.security;

import java.util.List;

record JwtResponse(
    String token,
    String username,
    List<String> roles
) {

}
