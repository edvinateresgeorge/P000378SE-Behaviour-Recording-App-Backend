package com.behaviourbridgetracks.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EmotionCheckInRequest {
    private String clientId;
    private String emotion;
    private Integer emotionLevel;
    private String notes;
    private LocalDateTime checkedAt;
}