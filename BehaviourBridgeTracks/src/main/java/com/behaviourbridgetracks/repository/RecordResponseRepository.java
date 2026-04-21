package com.behaviourbridgetracks.repository;

import com.behaviourbridgetracks.model.RecordResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RecordResponseRepository
        extends MongoRepository<RecordResponse, String> {
    List<RecordResponse> findByRecordId(String recordId);
}