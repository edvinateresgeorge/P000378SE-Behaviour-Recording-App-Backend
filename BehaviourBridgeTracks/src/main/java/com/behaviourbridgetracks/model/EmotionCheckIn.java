package com.behaviourbridgetracks.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "emotion_checkins")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class EmotionCheckIn {
    @Id
    private String id;
    private String clientId;
    private String userId;

    // Step 1 — emotion face selection
    // "Very Upset", "Upset", "Okay", "Good", "Great"
    private String emotion;
    private Integer emotionLevel; // 1-5

    // Step 2 — body signals (optional)
    // Categories: Anxiety/Stress, Low mood, Anger,
    // Shame, Excitement, Calm, Sensory Signals
    private List<String> bodySignalCategories;
    private List<String> bodySignals;

    // Optional notes
    private String notes;

    private LocalDateTime checkedAt;
    private LocalDateTime createdAt;
}