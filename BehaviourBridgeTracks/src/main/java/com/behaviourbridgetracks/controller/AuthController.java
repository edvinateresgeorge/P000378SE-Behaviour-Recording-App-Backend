package com.behaviourbridgetracks.controller;

import com.behaviourbridgetracks.dto.*;
import com.behaviourbridgetracks.model.User;
import com.behaviourbridgetracks.repository.UserRepository;
import com.behaviourbridgetracks.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(),
                user.getPasswordHash())) {
            return ResponseEntity.status(401)
                    .body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getRole());

        // Flutter receives this and routes to correct screen
        // ADMIN → Practitioner dashboard (see all clients)
        // CLIENT → Parent dashboard (see only their child)
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .userId(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build());
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(
            @RequestBody RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role("ADMIN")          // Practitioner / Staff
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return ResponseEntity.status(201)
                .body("Practitioner account created");
    }

    @PostMapping("/register/client")
    public ResponseEntity<?> registerClient(
            @RequestBody RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role("CLIENT")         // Parent
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        return ResponseEntity.status(201)
                .body("Parent account created");
    }
}