package com.behaviourbridgetracks.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role;        // "ADMIN" or "CLIENT" — sent from Flutter
}