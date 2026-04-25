package com.behaviourbridgetracks.dto;

import lombok.Data;

@Data
public class TaskRequest {
    private String title;
    private String category;
    private String status;
}
