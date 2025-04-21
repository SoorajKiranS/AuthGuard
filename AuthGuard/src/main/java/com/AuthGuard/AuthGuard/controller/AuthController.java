package com.AuthGuard.AuthGuard.controller;

import com.AuthGuard.AuthGuard.model.User;
import com.AuthGuard.AuthGuard.service.JwtService;
import com.AuthGuard.AuthGuard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    // Register using Map input (username, password, role)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String role = request.getOrDefault("role", "USER");

        if (userService.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User user = userService.registerUser(username, password, role);
        return ResponseEntity.ok(user);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        return userService.findByUsername(username)
                .filter(user -> jwtService.passwordMatches(password, user.getPassword()))
                .map(user -> {
                    String token = jwtService.generateToken(user);
                    return ResponseEntity.ok(Map.of("token", token));
                })
                .orElseGet(() -> {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("message", "Invalid credentials");
                    return ResponseEntity.status(401).body(errorResponse);
                });
    }

    // Alternate register using full User object — use a different endpoint to avoid ambiguity
    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }


    @GetMapping("/me")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return ResponseEntity.ok(Map.of(
                "username", userDetails.getUsername(),
                "roles", userDetails.getAuthorities()
        ));
    }
}
