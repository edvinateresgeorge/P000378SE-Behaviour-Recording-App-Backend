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
    private String emotion;
    private Integer emotionLevel;
    private List<String> feelings;
    private List<String> bodySignalCategories;
    private List<String> bodySignals;
    private String notes;
    private LocalDateTime checkedAt;
    private LocalDateTime createdAt;
}
