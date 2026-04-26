package com.behaviourbridgetracks.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "safety_plans")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SafetyPlan {
    @Id
    private String id;
    private String userId;
    private String clientId;

    // Emergency contacts — custom added by parent
    private List<EmergencyContact> emergencyContacts;

    // Warning signs — early indicators of dysregulation
    // e.g. "pacing", "covering ears"
    private List<String> warningSigns;

    // What helps — calming strategies
    // e.g. "deep pressure", "quiet space"
    private List<String> whatHelps;

    // Safe spaces — where child feels safe
    // e.g. "bedroom", "under the stairs"
    private List<String> safeSpaces;

    // Crisis helpline — pre-populated defaults
    private List<CrisisContact> crisisHelplines;

    // Additional notes for crisis
    private String additionalNotes;
}