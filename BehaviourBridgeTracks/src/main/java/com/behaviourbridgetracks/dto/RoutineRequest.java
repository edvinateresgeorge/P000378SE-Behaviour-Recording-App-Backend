package com.behaviourbridgetracks.dto;

import lombok.Data;
import java.util.List;

@Data
public class RoutineRequest {
    private String title;
    private String category;
    private Integer durationMinutes;
    private String startTime;
    private List<Integer> repeatDays;
    private List<StepRequest> steps;

    @Data
    public static class StepRequest {
        private String title;
        private Integer durationMin;
    }
}