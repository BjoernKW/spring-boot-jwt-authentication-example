package com.bjoernkw.springbootjwtauthenticationexample.security;

import jakarta.validation.constraints.NotBlank;

record LoginRequest(
    @NotBlank String username,
    @NotBlank String password
) {

}
