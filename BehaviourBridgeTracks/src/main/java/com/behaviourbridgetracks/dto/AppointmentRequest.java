package com.behaviourbridgetracks.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private String clientId;
    private String clientName;
    private String practitionerId;
    private String practitionerName;
    private String title;
    private String description;
    private String location;
    private String type;           // "IN_PERSON" or "ONLINE"
    private LocalDateTime appointmentDate;
    private Integer durationMinutes;
}