package com.behaviourbridgetracks.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "tasks")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Task {
    @Id
    private String id;
    private String userId;
    private String title;
    private String category;
    private String status; // pending, in_progress, completed
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;
    private Integer durationMinutes;
    private LocalDateTime dueDate;
    private String repeat;        // 'Once' | 'Daily' | 'Weekdays' | 'Weekly'
    private Integer difficulty;   // 0–5
    private LocalDateTime repeatEndDate;
}
