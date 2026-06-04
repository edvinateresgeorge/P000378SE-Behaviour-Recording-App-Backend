package com.behaviourbridgetracks.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "appointments")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Appointment {
    @Id
    private String id;

    // Who booked the appointment
    private String clientId;
    private String userId;       // parent user ID
    private String clientName;   // child name

    // Practitioner details
    private String practitionerId;
    private String practitionerName;

    // Appointment details
    private String title;        // e.g. "Behaviour Review Session"
    private String description;  // optional notes
    private String location;     // e.g. "Clinic Room 1", "Online"
    private String type;         // "IN_PERSON" or "ONLINE"

    // Date and time
    private LocalDateTime appointmentDate;
    private Integer durationMinutes; // e.g. 30, 60

    // Status tracking
    // PENDING, CONFIRMED, CANCELLED, COMPLETED
    private String status;

    // Notes added after appointment
    private String sessionNotes;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}