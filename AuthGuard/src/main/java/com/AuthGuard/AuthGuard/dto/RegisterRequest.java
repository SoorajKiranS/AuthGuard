package com.AuthGuard.AuthGuard.dto;
import lombok.Data;
import java.util.Set;

@Data
public class RegisterRequest {
//     Carries data when a new user signs up (registers).
    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}

