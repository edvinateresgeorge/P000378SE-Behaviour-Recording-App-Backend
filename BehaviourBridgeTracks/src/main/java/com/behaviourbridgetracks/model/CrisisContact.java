package com.behaviourbridgetracks.model;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CrisisContact {
    private String name;    // "Kids Helpline", "Lifeline"
    private String number;  // phone number
    private String action;  // "Call" or "Text"
}