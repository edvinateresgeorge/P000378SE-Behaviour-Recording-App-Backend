package com.behaviourbridgetracks.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EmotionCheckInRequest {
    private String clientId;
    private String emotion;
    private Integer emotionLevel;
    private List<String> feelings;
    private List<String> bodySignalCategories;
    private List<String> bodySignals;
    private String notes;
    private LocalDateTime checkedAt;
}
