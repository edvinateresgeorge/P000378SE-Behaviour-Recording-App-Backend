package com.behaviourbridgetracks.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "responses")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Response {
    @Id
    private String id;
    private String name;
    private String strategyType;
}