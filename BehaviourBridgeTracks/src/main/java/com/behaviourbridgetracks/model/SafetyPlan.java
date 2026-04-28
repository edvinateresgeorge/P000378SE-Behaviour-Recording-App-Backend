package com.behaviourbridgetracks.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "safety_plans")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SafetyPlan {
    @Id
    private String id;
    private String userId;
    private List<Contact> contacts;
    private List<String> warningSigns;
    private List<String> calmingStrategies;
    private List<String> safeSpaces;
    private String notes;
}
