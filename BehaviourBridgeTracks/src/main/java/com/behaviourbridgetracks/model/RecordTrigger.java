package com.behaviourbridgetracks.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "record_triggers")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RecordTrigger {
    @Id
    private String id;
    private String recordId;
    private String triggerId;
}