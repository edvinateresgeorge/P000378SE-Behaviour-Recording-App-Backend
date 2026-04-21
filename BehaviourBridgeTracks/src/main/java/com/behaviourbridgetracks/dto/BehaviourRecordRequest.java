package com.behaviourbridgetracks.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BehaviourRecordRequest {
    private String clientId;
    private Integer intensity;
    private LocalDateTime occurredAt;
    private String notes;
    private List<String> behaviourTypeIds;
    private List<String> triggerIds;
    private List<String> settingIds;
    private List<String> responseIds;

    // Extra fields from Flutter log screen
    // Stores names directly since Flutter uses names not IDs
    private String location;
    private List<String> behaviourNames;
    private List<String> triggerNames;
    private List<String> strategyNames;
    private List<String> replacementNames;
}