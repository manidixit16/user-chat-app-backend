package com.chatapp.controller;

import com.chatapp.model.LoginRequest;
import com.chatapp.model.User;
import com.chatapp.services.UserService;
import com.chatapp.util.JwtUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error during registration: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            User user = userService.findByUsername(loginRequest.getUsername());
            if (user != null && userService.validatePassword(user, loginRequest.getPassword())) {
                // Generate JWT token
                UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
                String token = jwtUtil.generateToken(userDetails);
                return ResponseEntity.ok(new JwtResponse(token));
            } else {
                return ResponseEntity.status(401).body("Invalid username or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Stub: Invalidate JWT (e.g., add token to a blacklist)
        return ResponseEntity.ok("Logged out successfully");
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage());
        }
    }

    // Inner class for JWT response
    @Getter
    private static class JwtResponse {
        private final String token;

        public JwtResponse(String token) {
            this.token = token;
        }
    }
}