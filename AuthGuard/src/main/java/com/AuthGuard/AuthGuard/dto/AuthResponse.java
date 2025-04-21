package com.AuthGuard.AuthGuard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
//    Sends back the generated JWT token after login or registration.
    private String token;
}
