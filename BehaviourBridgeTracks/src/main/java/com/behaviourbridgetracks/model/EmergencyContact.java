package com.behaviourbridgetracks.model;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EmergencyContact {
    private String name;
    private String number;
    private String role;  // e.g. "Doctor", "Therapist"
}
