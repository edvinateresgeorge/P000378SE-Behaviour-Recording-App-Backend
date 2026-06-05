package com.behaviourbridgetracks.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {
    private String title;
    private String category;
    private String status;
    private String description;
    private Integer durationMinutes;
    private LocalDateTime dueDate;
    private String repeat;        // 'Once' | 'Daily' | 'Weekdays' | 'Weekly'
    private Integer difficulty;// 0–5
    private LocalDateTime repeatEndDate;
}
