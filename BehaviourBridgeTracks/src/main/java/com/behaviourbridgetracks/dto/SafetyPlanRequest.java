package com.behaviourbridgetracks.dto;

import lombok.Data;
import java.util.List;

@Data
public class SafetyPlanRequest {
    private List<ContactDto> contacts;
    private List<String> warningSigns;
    private List<String> calmingStrategies;
    private List<String> safeSpaces;
    private String notes;
    private List<ContactDto> crisisContacts;

    @Data
    public static class ContactDto {
        private String name;
        private String number;
    }
}
