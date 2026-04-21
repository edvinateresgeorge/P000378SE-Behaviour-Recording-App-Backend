package com.behaviourbridgetracks.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "behaviour_records")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BehaviourRecord {
    @Id
    private String id;
    private String clientId;
    private String userId;
    private Integer intensity;
    private LocalDateTime occurredAt;
    private LocalDateTime createdAt;
    private String notes;
    private String location;

    // Store names directly from Flutter
    private List<String> behaviourNames;
    private List<String> triggerNames;
    private List<String> strategyNames;
    private List<String> replacementNames;
}