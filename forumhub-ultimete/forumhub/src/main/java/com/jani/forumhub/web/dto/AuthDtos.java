package com.jani.forumhub.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthDtos {
    public record RegisterRequest(@NotBlank String name, @Email String email, @NotBlank String password){}
    public record LoginRequest(@Email String email, @NotBlank String password){}
    public record TokenResponse(String token){}
}
