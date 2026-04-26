package com.behaviourbridgetracks.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EmotionCheckInRequest {
    private String clientId;

    // Emotion face selected
    private String emotion;
    private Integer emotionLevel;

    // Body signals selected (optional)
    private List<String> bodySignalCategories;
    private List<String> bodySignals;

    // Optional notes
    private String notes;

    private LocalDateTime checkedAt;
}