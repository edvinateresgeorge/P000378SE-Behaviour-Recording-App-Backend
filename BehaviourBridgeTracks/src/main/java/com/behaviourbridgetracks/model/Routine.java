package com.behaviourbridgetracks.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "routines")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Routine {
    @Id
    private String id;
    private String userId;
    private String title;
    private String category; // Morning, Bedtime, Homework, After School, Custom
    private LocalDateTime startDate;
    private String startTime; // "HH:mm" e.g. "07:00"
    @Builder.Default
    private List<Integer> repeatDays = new ArrayList<>(); // 0=Mon ... 6=Sun
    @Builder.Default
    private List<Step> steps = new ArrayList<>();
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Step {
        private String title;
        private Integer durationMin;
    }
}
