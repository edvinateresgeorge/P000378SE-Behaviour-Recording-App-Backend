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
    private String clientId;   // child being checked
    private String userId;     // parent who logged it
    private String emotion;    // "Very Upset", "Upset", "Okay", "Good", "Great"
    private Integer emotionLevel; // 1-5
    private List<String> feelings;
    private String notes;
    private LocalDateTime checkedAt;
    private LocalDateTime createdAt;
}

