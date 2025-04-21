package com.AuthGuard.AuthGuard.dto;
import lombok.Data;

@Data
public class LoginRequest {
//     Carries data when a user logs in (username and password).
    private String username;
    private String password;
}
