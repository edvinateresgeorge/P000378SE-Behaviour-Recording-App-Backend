package com.behaviourbridgetracks.dto;

import lombok.*;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String userId;
    private String name;
    private String role;        // Flutter uses this to route to correct screen
}