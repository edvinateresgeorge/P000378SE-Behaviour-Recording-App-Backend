package com.behaviourbridgetracks.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RoutineRequest {
    private String title;
    private String category;
    private LocalDateTime startDate;
    private String startTime;
    private List<Integer> repeatDays;
    private List<StepRequest> steps;
    private LocalDateTime endDate;

    @Data
    public static class StepRequest {
        private String title;
        private Integer durationMin;
    }
}